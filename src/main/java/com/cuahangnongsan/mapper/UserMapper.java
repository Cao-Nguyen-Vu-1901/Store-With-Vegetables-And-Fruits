package com.cuahangnongsan.mapper;

import com.cuahangnongsan.entity.User;
import com.cuahangnongsan.dto.request.UserRequest;
import com.cuahangnongsan.dto.response.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser (UserRequest request);

    UserResponse toUserResponse(User user);
}
