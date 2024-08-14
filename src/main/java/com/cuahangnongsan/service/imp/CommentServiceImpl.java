package com.cuahangnongsan.service.imp;

import com.cuahangnongsan.dto.response.CommentResponse;
import com.cuahangnongsan.dto.response.ProductResponse;
import com.cuahangnongsan.dto.response.UserResponse;
import com.cuahangnongsan.entity.Comment;
import com.cuahangnongsan.entity.Product;
import com.cuahangnongsan.entity.User;
import com.cuahangnongsan.mapper.CommentMapper;
import com.cuahangnongsan.repository.CommentRepository;
import com.cuahangnongsan.repository.ProductRepository;
import com.cuahangnongsan.repository.UserRepository;
import com.cuahangnongsan.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements ICommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public CommentResponse save(Comment comment, UserResponse user) {
        comment.setCreatedDate(LocalDateTime.now());
        if(user != null){
            comment.setUsername(user.getUsername());
            comment.setImageUser(user.getImage());
        }
        Product product = productRepository.findById(comment.getProduct().getId()).orElseThrow();
        comment.setProduct(product);
        return commentMapper.toCommentResponse(commentRepository.save(comment));
    }
    @Override
    public List<CommentResponse> findAll() {
        return commentRepository.findAll()
                .stream().map(commentMapper::toCommentResponse)
                .collect(Collectors.toList());
    }
    @Override
    public List<CommentResponse> findAllByProductId(String id) {
        Product product = productRepository.findById(id).orElseThrow();
        return commentRepository.findAllByProduct(product)
                .stream().map(commentMapper::toCommentResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentResponse> findAllByCreatedDateBefore(LocalDateTime time) {
        return commentRepository.findAllByCreatedDateBefore(time)
                .stream().map(commentMapper::toCommentResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentResponse> findAllByUsernameLike(String username) {
        return commentRepository.findAllByUsernameLike(username)
                .stream().map(commentMapper::toCommentResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentResponse> findAllByContentLike(String content) {
        return commentRepository.findAllByContentLike(content)
                .stream().map(commentMapper::toCommentResponse)
                .collect(Collectors.toList());
    }

    public CommentResponse findById(Long id) {
        return commentMapper.toCommentResponse(commentRepository.findById(id).orElse(null));
    }


    @PreAuthorize("hasAuthority('AUTHORITY_DELETE_COMMENT')")
    @Override
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }

}

