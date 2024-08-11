package com.cuahangnongsan.service;

import com.cuahangnongsan.entity.Comment;
import com.cuahangnongsan.entity.Product;

import java.time.LocalDateTime;
import java.util.List;

public interface ICommentService {

    List<Comment> findAll();
    List<Comment> findAllByProduct(Product product);
    List<Comment> findAllByCreatedDateBefore(LocalDateTime time);
    List<Comment> findAllByUsernameLike(String username);
    List<Comment> findAllByContentLike(String content);

    Comment findById(Long id);

    Comment save(Comment comment);
    void delete(Comment comment);


}
