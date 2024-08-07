package com.cuahangnongsan.service.imp;

import com.cuahangnongsan.entity.Post;
import com.cuahangnongsan.repository.PostRepository;
import com.cuahangnongsan.service.IPostService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostServiceImpl implements IPostService {

    @Autowired
    PostRepository postRepository;


    @PreAuthorize("hasAuthority('AUTHORITY_CREATE_POST')")
    @Override
    public void save(Post post) {
        postRepository.save(post);
    }

    @PreAuthorize("hasAuthority('AUTHORITY_DELETE_POST')")
    @Override
    public void delete(Post post) {
        postRepository.delete(post);
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }


    @Override
    public Post findById(String id) {
        return postRepository.findById(id).orElseThrow();
    }
}
