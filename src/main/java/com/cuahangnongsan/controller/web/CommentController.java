package com.cuahangnongsan.controller.web;

import com.cuahangnongsan.entity.Comment;
import com.cuahangnongsan.entity.Product;
import com.cuahangnongsan.entity.User;
import com.cuahangnongsan.service.ICommentService;
import com.cuahangnongsan.service.IProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private ICommentService commentService;

    @Autowired
    private IProductService productService;

    @PostMapping
    public ResponseEntity<Comment> addComment(@RequestBody Comment comment, HttpSession modelMap) {
        User user = (User) modelMap.getAttribute("user");
        comment.setCreateDate(LocalDateTime.now());
        if(user != null){
            comment.setUsername(user.getUsername());
            comment.setImageUser(user.getImage());
        }
        Product product = productService.findById(comment.getProduct().getId());
        comment.setProduct(product);
        Comment savedComment = commentService.saveComment(comment);
        return ResponseEntity.ok(savedComment);
    }

    @GetMapping("/{id}")
    public List<Comment> getAllParentComments(@PathVariable String id) {
        Product product = productService.findById(id);
        List<Comment> allComments = commentService.findAllByProduct(product);
        return allComments.stream()
                .filter(comment -> comment.getParent() == null)
                .collect(Collectors.toList());
    }


    @GetMapping
    public List<Comment> getAllCommentsByProduct() {
        List<Comment> allComments = commentService.findAllComments();
        return allComments.stream()
                .filter(comment -> comment.getParent() == null)
                .collect(Collectors.toList());
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Comment> getComment(@PathVariable Long id) {
//        Comment comment = commentService.getCommentById(id);
//        if (comment != null) {
//            return ResponseEntity.ok(comment);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
}
