package com.cuahangnongsan.controller.web;

import com.cuahangnongsan.dto.response.CommentResponse;
import com.cuahangnongsan.dto.response.ProductResponse;
import com.cuahangnongsan.dto.response.UserResponse;
import com.cuahangnongsan.entity.Comment;
import com.cuahangnongsan.entity.Product;
import com.cuahangnongsan.entity.User;
import com.cuahangnongsan.service.ICommentService;
import com.cuahangnongsan.service.IProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CommentResponse> addComment(@RequestBody Comment comment, HttpSession modelMap) {
        UserResponse user = (UserResponse) modelMap.getAttribute("user");
        CommentResponse savedComment = commentService.save(comment, user);
        return ResponseEntity.ok(savedComment);
    }
    @DeleteMapping
    public void deleteComment(@RequestBody Comment comment) {
        CommentResponse cmt = commentService.findById(comment.getId());
        commentService.deleteById(cmt.getId());
    }


    @GetMapping("/{id}")
    public List<CommentResponse> getAllParentComments(@PathVariable String id) {
        List<CommentResponse> allComments = commentService.findAllByProductId(id);
        return allComments.stream()
                .filter(comment -> comment.getParent() == null)
                .collect(Collectors.toList());
    }


    @GetMapping
    public List<CommentResponse> getAllCommentsByProduct() {
        List<CommentResponse> allComments = commentService.findAll();
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
