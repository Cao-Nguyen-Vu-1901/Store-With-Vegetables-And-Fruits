package com.cuahangnongsan.service;

import com.cuahangnongsan.dto.response.RoleResponse;
import com.cuahangnongsan.entity.Permission;
import com.cuahangnongsan.entity.Role;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
