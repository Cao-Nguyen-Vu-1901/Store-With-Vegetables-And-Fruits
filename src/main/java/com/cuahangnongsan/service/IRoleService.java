package com.cuahangnongsan.service;

import com.cuahangnongsan.entity.Permission;
import com.cuahangnongsan.entity.Role;

import java.util.List;
import com.cuahangnongsan.dto.response.*;
public interface IRoleService {
    List<RoleResponse> findAll();
    List<RoleResponse> findByNameLike(String name);
    RoleResponse findByName(String name);
    RoleResponse findById(String id);

    List<RoleResponse> findAllByName(String name);

    RoleResponse save(String id, String name,
                      List<String> permissionsNew);

    void update(String id, List<String> permissions);

    void delete(Role role);

    void removePermissionsFromRole(Role role, Permission permission);
}
