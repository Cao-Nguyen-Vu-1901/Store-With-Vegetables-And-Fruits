package com.cuahangnongsan.controller.admin;


import com.cuahangnongsan.entity.Category;
import com.cuahangnongsan.entity.Post;
import com.cuahangnongsan.entity.Product;
import com.cuahangnongsan.entity.User;
import com.cuahangnongsan.repository.UserRepository;
import com.cuahangnongsan.service.IPostService;
import com.cuahangnongsan.service.IUserService;
import com.cuahangnongsan.util.ProcessImage;
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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/admin/post")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostManageController {

    @Autowired
    IPostService postService;

    @Autowired
    IUserService userService;


    @GetMapping("/manage-post")
    public String showPost(ModelMap modelMap){
        modelMap.addAttribute("posts", postService.findAll());
        return "admin/manage/manage-post";
    }

    @PostMapping("/manage-post")
    public String managePost(ModelMap modelMap, String type, String id){
        Post post = id != null ? postService.findById(id) : null;
        if(type.equals("delete")){
            postService.delete(post);
            return "admin/manage/manage-post";
        }else {
            modelMap.addAttribute("post", post);
            return "redirect:/admin/post/update-post" ;
        }
    }

    @GetMapping("/create-post")
    public String createPost(ModelMap modelMap){
        return "admin/create/create-post";
    }

    @PostMapping("update-post")
    public String updatePost(ModelMap modelMap, String id, String title,
                             String content, MultipartFile image, String shortDescription ) throws IOException {

        String currentDir = System.getProperty("user.dir");
        Path resourcePath = Paths.get(currentDir, "src", "main", "resources\\static\\img\\posts");

        Post oldPost = null;
        String imageName  = !Objects.equals(image.getOriginalFilename(), "")
                ? ProcessImage.saveImageInServer(image,resourcePath)
                : "";
        LocalDateTime createdTime = LocalDateTime.now();
        if(id != null){
            oldPost  = postService.findById(id);

        }

        Post post = Post.builder().id(id).title(title).content(content)
                .shortDescription(shortDescription).modifiedTime(LocalDateTime.now())
                .user((User) modelMap.getAttribute("user"))
                .image(oldPost != null ? oldPost.getImage() : imageName)
                .createdTime(oldPost != null ? oldPost.getCreatedTime() : createdTime)
                .build();


        postService.save(post);
        return "redirect:/admin/post/manage-post" ;
    }


    @ModelAttribute
    public void commonUser(Principal p, Model m, HttpSession session) {
        if (p != null) {
            String username = p.getName();
            User user = userService.findByUsername(username);
            if (user != null) {
                session.setAttribute("user", user);
                m.addAttribute("user", user);
            }


        }
    }
}
