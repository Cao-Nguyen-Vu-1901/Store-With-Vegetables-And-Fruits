package com.cuahangnongsan.service;

import com.cuahangnongsan.entity.Post;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface IPostService {

    void save(Post post);
    void delete(Post post);
    List<Post> findAll();
    Post findById(String id);
}
