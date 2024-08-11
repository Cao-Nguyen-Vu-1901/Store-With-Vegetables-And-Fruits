package com.cuahangnongsan.mapper;

import com.cuahangnongsan.entity.Role;
import com.cuahangnongsan.modal.request.RoleRequest;
import com.cuahangnongsan.modal.response.RoleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface RoleMapper {

    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);
    RoleResponse toRoleResponse(Role role);
}
