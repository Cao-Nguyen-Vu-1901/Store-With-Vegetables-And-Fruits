package com.cuahangnongsan.service;

import com.cuahangnongsan.entity.Permission;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface IPermissionService {
    List<Permission> findAll();
    Permission findById(String id);


    void save(Permission permission);
}
