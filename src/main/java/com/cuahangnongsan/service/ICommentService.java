package com.cuahangnongsan.service;

import com.cuahangnongsan.entity.Comment;
import com.cuahangnongsan.entity.Product;

import java.util.List;

public interface ICommentService {
    Comment saveComment(Comment comment);

    List<Comment> findAllComments();
    List<Comment> findAllByProduct(Product product);

    Comment getCommentById(Long id);

    void delete(Comment comment);


}
