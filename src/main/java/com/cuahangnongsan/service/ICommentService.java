package com.cuahangnongsan.service;

import com.cuahangnongsan.entity.Comment;
import com.cuahangnongsan.entity.Product;

import java.util.List;

public interface ICommentService {
    public Comment saveComment(Comment comment);

    public List<Comment> findAllComments();
    public List<Comment> findAllByProduct(Product product);

    public Comment getCommentById(Long id);
}
