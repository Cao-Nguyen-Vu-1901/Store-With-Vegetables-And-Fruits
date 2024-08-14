package com.cuahangnongsan.mapper;


import com.cuahangnongsan.dto.request.CommentRequest;
import com.cuahangnongsan.dto.response.CommentResponse;
import com.cuahangnongsan.entity.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment toComment(CommentRequest request);
    CommentResponse toCommentResponse(Comment comment);
}
