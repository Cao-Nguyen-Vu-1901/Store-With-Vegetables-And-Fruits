package com.cuahangnongsan.service;

import com.cuahangnongsan.entity.Permission;
import com.cuahangnongsan.entity.Role;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface IPermissionService {
    List<Permission> findAll();
    Permission findById(String id);
    Permission findByName(String name);


    void save(Permission permission);
}
