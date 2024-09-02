package com.cuahangnongsan.mapper;


import com.cuahangnongsan.entity.Post;
import com.cuahangnongsan.dto.request.PostRequest;
import org.mapstruct.Mapper;
import com.cuahangnongsan.dto.response.*;
@Mapper(componentModel = "spring")
public interface PostMapper {
    Post toPost(PostRequest request);
    PostResponse toPostResponse(Post post);
}
