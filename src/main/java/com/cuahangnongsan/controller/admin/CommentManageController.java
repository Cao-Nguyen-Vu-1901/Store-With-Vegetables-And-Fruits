package com.cuahangnongsan.controller.admin;

import com.cuahangnongsan.entity.Comment;
import com.cuahangnongsan.entity.Product;
import com.cuahangnongsan.entity.User;
import com.cuahangnongsan.service.ICommentService;
import com.cuahangnongsan.service.IProductService;
import com.cuahangnongsan.service.IUserService;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/comment")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentManageController {

    @Autowired
    ICommentService commentService;

    @Autowired
    IProductService productService;

    @Autowired
    IUserService userService;
    @GetMapping("/manage-comment")
    public String showComments(ModelMap modelMap, String value, String type){

        List<Comment> comments;
        if(type!= null & value != null){
            switch (type) {
                case "content" -> comments = commentService.findAllByContentLike("%" + value + "%");
                case "createdDate" -> comments = commentService.findAllByCreatedDateBefore(LocalDateTime.parse(value));
                case "username" -> comments = commentService.findAllByUsernameLike("%" + value + "%");
                case "product" -> {
                                        comments = new ArrayList<>();
                                        List<Product> products =
                                                productService.findAllByNameLike(value);
                                        products.forEach(a -> {
                                            List<Comment> subComment =
                                                    commentService.findAllByProduct(a);
                                            comments.addAll(subComment);
                                        });
                                    }
                default -> {
                    comments = null;
                }
            }
        }else{
            comments = commentService.findAll();
        }
        modelMap.addAttribute("comments", comments);
        return "admin/manage/manage-comment";
    }

    @PostMapping("/delete-comment")
    public String deleteComment(ModelMap modelMap, Long id){
        Comment comment = commentService.getCommentById(id);
        commentService.delete(comment);
        return "redirect:/admin/comment/manage-comment";
    }

    @ModelAttribute
    public void commonUser(Principal p, Model m, HttpSession session) {
        if (p != null) {
            String username = p.getName();
            User user = userService.findByUsername(username);
            if (user != null){
                session.setAttribute("user", user);
                m.addAttribute("user", user);
            }
        }
    }
}
