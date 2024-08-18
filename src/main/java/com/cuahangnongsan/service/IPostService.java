package com.cuahangnongsan.service;

import com.cuahangnongsan.dto.request.PostRequest;
import com.cuahangnongsan.entity.Post;
import com.cuahangnongsan.entity.User;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import com.cuahangnongsan.dto.response.*;
public interface IPostService {

    PostResponse save(String id, MultipartFile image, PostRequest request
            , RedirectAttributes redirectAttributes , User user) throws IOException;
    void delete(Post post);
    List<Post> findAll();
    Post findById(String id);
}
