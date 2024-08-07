package com.cuahangnongsan.service.imp;

import com.cuahangnongsan.entity.Permission;
import com.cuahangnongsan.repository.PermissionRepository;
import com.cuahangnongsan.service.IPermissionService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionServiceImpl implements IPermissionService {

    @Autowired
    PermissionRepository permissionRepository;

    @Override
    public List<Permission> findAll() {
        return permissionRepository.findAll();
    }

    @Override
    public Permission findById(String id) {
        return permissionRepository.findById(id).orElseThrow();
    }


    @PreAuthorize("hasAuthority('AUTHORITY_CREATE_PERMISSION')")
    @Override
    public void save(Permission permission) {
        permissionRepository.save(permission);
    }
}
