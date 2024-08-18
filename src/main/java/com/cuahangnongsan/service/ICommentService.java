package com.cuahangnongsan.service;

import com.cuahangnongsan.entity.Comment;

import java.time.LocalDateTime;
import java.util.List;
import com.cuahangnongsan.dto.response.*;
public interface ICommentService {

    List<CommentResponse> findAll();
    List<CommentResponse> findAllByProductId(String id);
    List<CommentResponse> findAllByCreatedDateBefore(LocalDateTime time);
    List<CommentResponse> findAllByUsernameLike(String username);
    List<CommentResponse> findAllByContentLike(String content);

    CommentResponse findById(Long id);

    CommentResponse save(Comment comment, UserResponse user);
    void deleteById(Long id);


}
