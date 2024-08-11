package com.cuahangnongsan.mapper;

import com.cuahangnongsan.entity.Permission;
import com.cuahangnongsan.dto.request.PermissionRequest;
import com.cuahangnongsan.dto.response.PermissionResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission (PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
}
