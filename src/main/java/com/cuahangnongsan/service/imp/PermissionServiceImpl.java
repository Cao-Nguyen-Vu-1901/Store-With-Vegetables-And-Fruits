package com.cuahangnongsan.service.imp;

import com.cuahangnongsan.entity.Permission;
import com.cuahangnongsan.mapper.PermissionMapper;
import com.cuahangnongsan.repository.PermissionRepository;
import com.cuahangnongsan.service.IPermissionService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import com.cuahangnongsan.dto.response.*;
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionServiceImpl implements IPermissionService {

    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    PermissionMapper permissionMapper;

    @Override
    public List<PermissionResponse> findAll() {
        return permissionRepository.findAll()
                .stream().map(permissionMapper::toPermissionResponse)
                .collect(Collectors.toList());
    }


    @Override
    public PermissionResponse findById(String id) {
        return permissionMapper.toPermissionResponse(permissionRepository.findById(id).orElseThrow());
    }


    @Override
    public PermissionResponse findByName(String name) {
        return permissionMapper.toPermissionResponse(permissionRepository.findByName(name));
    }


    @PreAuthorize("hasAuthority('AUTHORITY_CREATE_PERMISSION')")
    @Override
    public PermissionResponse save(Permission permission) {
        return permissionMapper.toPermissionResponse(permissionRepository.save(permission));
    }

    @Override
    public void deleteById(String id) {
        permissionRepository.deleteById(id);
    }
}
