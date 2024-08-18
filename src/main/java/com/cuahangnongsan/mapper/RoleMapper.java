package com.cuahangnongsan.mapper;

import com.cuahangnongsan.entity.Role;
import com.cuahangnongsan.dto.request.RoleRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.cuahangnongsan.dto.response.*;
@Mapper
public interface RoleMapper {

    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);
    RoleResponse toRoleResponse(Role role);
}
