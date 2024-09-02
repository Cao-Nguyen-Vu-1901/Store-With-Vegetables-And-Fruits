package com.cuahangnongsan.mapper;

import com.cuahangnongsan.dto.request.AdminUserCreationRequest;
import com.cuahangnongsan.entity.User;
import com.cuahangnongsan.dto.request.UserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.cuahangnongsan.dto.response.*;
@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser (UserRequest request);

    @Mapping(target = "image", ignore = true)
    User toUser (AdminUserCreationRequest request);

    UserResponse toUserResponse(User user);
//    @Mapping(target = "roles", ignore = true)
//    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
