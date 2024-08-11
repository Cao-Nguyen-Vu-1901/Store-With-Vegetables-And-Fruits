package com.cuahangnongsan.service.imp;

import com.cuahangnongsan.entity.Comment;
import com.cuahangnongsan.entity.Product;
import com.cuahangnongsan.repository.CommentRepository;
import com.cuahangnongsan.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements ICommentService {

    @Autowired
    private CommentRepository commentRepository;

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }
    public List<Comment> findAllByProduct(Product product) {
        return commentRepository.findAllByProduct( product );
    }

    @Override
    public List<Comment> findAllByCreatedDateBefore(LocalDateTime time) {
        return commentRepository.findAllByCreatedDateBefore(time);
    }

    @Override
    public List<Comment> findAllByUsernameLike(String username) {
        return commentRepository.findAllByUsernameLike(username);
    }

    @Override
    public List<Comment> findAllByContentLike(String content) {
        return commentRepository.findAllByContentLike(content);
    }

    public Comment findById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }


    @PreAuthorize("hasAuthority('AUTHORITY_DELETE_COMMENT')")
    @Override
    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }

}

