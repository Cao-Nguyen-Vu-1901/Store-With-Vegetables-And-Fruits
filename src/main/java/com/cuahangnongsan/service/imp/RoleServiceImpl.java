package com.cuahangnongsan.service.imp;

import com.cuahangnongsan.entity.Permission;
import com.cuahangnongsan.entity.Role;
import com.cuahangnongsan.exception.AppException;
import com.cuahangnongsan.mapper.RoleMapper;
import com.cuahangnongsan.repository.PermissionRepository;
import com.cuahangnongsan.repository.RoleRepository;
import com.cuahangnongsan.service.IRoleService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import com.cuahangnongsan.dto.response.*;
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleServiceImpl implements IRoleService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    RoleMapper roleMapper;


    @Override
    public List<RoleResponse> findAll() {
        return roleRepository.findAll()
                .stream().map(roleMapper::toRoleResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoleResponse> findByNameLike(String name) {
        return roleRepository.findByNameLike(name)
                .stream().map(roleMapper::toRoleResponse)
                .collect(Collectors.toList());
    }

    @Override
    public RoleResponse findByName(String name) {
        return roleMapper.toRoleResponse(roleRepository.findByName(name));
    }


    @Override
    public RoleResponse findById(String id) {
        return roleMapper.toRoleResponse(roleRepository.findById(id).orElseThrow());
    }

    @Override
    public List<RoleResponse> findAllByName(String name) {
        return roleRepository.findAllByName(name)
                .stream().map(roleMapper::toRoleResponse)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAuthority('AUTHORITY_CREATE_ROLE')")
    @Override
    public RoleResponse save(String id, String name,
                             List<String> permissionsNew) {
        Set<Permission> permissionSet = new HashSet<>();
        Role role = new Role();
        Role roleCheck = roleRepository.findByName("ROLE_ADMIN_" + name);
        if(id == null && roleCheck != null){
            throw new AppException("Role is existed!");
        }else if(id != null && !roleCheck.getId().equals(id)){
            throw new AppException("Role is existed!");
        }
        if (id != null) {
            role = roleRepository.findById(id).orElseThrow();
            permissionSet.addAll(role.getPermissions());
        }

        if (permissionsNew != null) {
            List<String> permissions = new ArrayList<>(permissionsNew);
            permissions.forEach(a -> {
                Permission permission = permissionRepository.findById(a).orElseThrow();
                permissionSet.add(permission);
            });
        }
        role.setName("ROLE_ADMIN_" + name);
        role.setPermissions(permissionSet);
        roleRepository.save(role);
        return roleMapper.toRoleResponse(roleRepository.save(role));
    }

    @Override
    public void update(String id, List<String> permissions) {
        Role role = roleRepository.findById(id).orElseThrow();
        permissions.forEach(a -> {
            Permission permission = permissionRepository.findById(a).orElseThrow();
            role.getPermissions().remove(permission);
        });
        roleRepository.save(role);
    }

    @PreAuthorize("hasAuthority('AUTHORITY_DELETE_ROLE')")
    @Override
    public void delete(Role role) {
        roleRepository.delete(role);
    }


    @Override
    public void removePermissionsFromRole(Role role, Permission permission) {
        role.getPermissions().remove(permission);
        roleRepository.save(role);
    }
}
