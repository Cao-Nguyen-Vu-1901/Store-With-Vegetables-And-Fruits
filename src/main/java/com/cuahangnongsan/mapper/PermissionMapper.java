package com.cuahangnongsan.mapper;

import com.cuahangnongsan.entity.Permission;
import com.cuahangnongsan.dto.request.PermissionRequest;
import org.mapstruct.Mapper;
import com.cuahangnongsan.dto.response.*;
@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission (PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
}
