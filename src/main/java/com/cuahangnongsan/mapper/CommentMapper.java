package com.cuahangnongsan.mapper;


import com.cuahangnongsan.dto.request.CommentRequest;
import com.cuahangnongsan.entity.Comment;
import com.cuahangnongsan.entity.Product;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.mapstruct.Mapper;
import com.cuahangnongsan.dto.response.*;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment toComment(CommentRequest request);

//
//    @Mapping(target = "product", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "product.invoiceDetail", ignore = true)
    @Mapping(target = "product.comments", ignore = true)
    CommentResponse toCommentResponse(Comment comment);


}
