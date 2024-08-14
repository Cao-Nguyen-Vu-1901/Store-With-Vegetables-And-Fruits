package com.cuahangnongsan.service;

import com.cuahangnongsan.dto.response.PermissionResponse;
import com.cuahangnongsan.entity.Permission;
import com.cuahangnongsan.entity.Role;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface IPermissionService {
    List<PermissionResponse> findAll();

    PermissionResponse findById(String id);

    PermissionResponse findByName(String name);


    PermissionResponse save(Permission permission);

    void deleteById(String id);
}
