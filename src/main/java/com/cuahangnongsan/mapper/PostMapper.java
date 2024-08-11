package com.cuahangnongsan.mapper;


import com.cuahangnongsan.entity.Post;
import com.cuahangnongsan.modal.request.PostRequest;
import com.cuahangnongsan.modal.response.PostResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    Post toPost(PostRequest request);
    PostResponse toPostResponse(Post post);
}
