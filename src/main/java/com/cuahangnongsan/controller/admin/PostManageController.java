package com.cuahangnongsan.controller.admin;


import com.cuahangnongsan.dto.request.PostRequest;
import com.cuahangnongsan.entity.Post;
import com.cuahangnongsan.entity.User;
import com.cuahangnongsan.service.IPostService;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.awt.image.ImagingOpException;
import java.io.IOException;
import java.security.Principal;
import com.cuahangnongsan.dto.response.*;
@Controller
@RequestMapping("/admin/posts")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostManageController {

    @Autowired
    IPostService postService;

    @Autowired
    IUserService userService;



    @GetMapping("/manage-posts")
    public String showPost(ModelMap modelMap){
        modelMap.addAttribute("posts", postService.findAll());
        return "admin/manage/manage-post";
    }

    @PostMapping("/manage-posts")
    public String managePost(ModelMap modelMap, String action, String id,
                             RedirectAttributes redirectAttributes){
        Post post = id != null ? postService.findById(id) : null;
        if(action.equals("delete")){
            postService.delete(post);
            return "redirect:/admin/posts/manage-posts";
        }else {
            redirectAttributes.addFlashAttribute("post", post);
            return "redirect:/admin/posts/create-post" ;
        }
    }

    @GetMapping("/create-post")
    public String createPost(ModelMap modelMap){
        Post post = modelMap.getAttribute("post") != null
                        ? (Post) modelMap.getAttribute("post")
                        : null;
        modelMap.addAttribute("post", post);
        return "admin/create/create-post";
    }

//    @PostMapping("update-post")
//    public String updatePost(ModelMap modelMap, String id, String title,
//                             String content, MultipartFile image, String shortDescription
//                             , RedirectAttributes redirectAttributes     ) throws IOException {
//
//        if(Objects.equals(image.getOriginalFilename(), "") || content == null){
//            redirectAttributes.addFlashAttribute("post",
//                    Post.builder().title(title).content(content)
//                            .shortDescription(shortDescription).build());
//        }
//
//
//
//        Post oldPost = null;
//        String imageName  = "";
//        LocalDateTime createdTime = LocalDateTime.now();
//        if(id != null){
//            oldPost  = postService.findById(id);
//            imageName = !Objects.equals(image.getOriginalFilename(), "")
//                    ? ProcessImage.upload(image,"/posts")
//                    : oldPost.getImage();
//        }
//
//        Post post = Post.builder().id(id).title(title).content(content)
//                .shortDescription(shortDescription).modifiedTime(LocalDateTime.now())
//                .user((User) modelMap.getAttribute("user"))
//                .image(imageName)
//                .createdTime(oldPost != null ? oldPost.getCreatedTime() : createdTime)
//                .build();
//
//
//        postService.save(post);
//        return "redirect:/admin/post/create-post" ;
//    }

    @PostMapping("update-post")
    public String updatePost(ModelMap modelMap, String id, MultipartFile image, PostRequest request
            , RedirectAttributes redirectAttributes  ){

        try {
            postService.save(id, image, request, redirectAttributes, (User) modelMap.getAttribute("user"));
        } catch (IOException | ImagingOpException e) {
            return "redirect:/admin/posts/create-post" ;
        }

        return "redirect:/admin/posts/create-post" ;
    }

    @ModelAttribute
    public void commonUser(Principal p, Model m, HttpSession session) {
        if (p != null) {
            String username = p.getName();
            UserResponse user = userService.findByUsername(username);
            if (user != null) {
                m.addAttribute("user", user);
            }


        }
    }
}
