package com.cuahangnongsan.service.imp;

import com.cuahangnongsan.entity.Comment;
import com.cuahangnongsan.entity.Product;
import com.cuahangnongsan.repository.CommentRepository;
import com.cuahangnongsan.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements ICommentService {

    @Autowired
    private CommentRepository commentRepository;

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public List<Comment> findAllComments() {
        return commentRepository.findAll();
    }
    public List<Comment> findAllByProduct(Product product) {
        return commentRepository.findAllByProduct( product );
    }

    public Comment getCommentById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }

}

