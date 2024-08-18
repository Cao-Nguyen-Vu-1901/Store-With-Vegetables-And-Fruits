package com.cuahangnongsan.service;

import com.cuahangnongsan.entity.Permission;

import java.util.List;
import com.cuahangnongsan.dto.response.*;
public interface IPermissionService {
    List<PermissionResponse> findAll();

    PermissionResponse findById(String id);

    PermissionResponse findByName(String name);


    PermissionResponse save(Permission permission);

    void deleteById(String id);
}
