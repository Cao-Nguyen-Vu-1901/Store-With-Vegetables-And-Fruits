package com.cuahangnongsan.service;

import com.cuahangnongsan.entity.Permission;
import com.cuahangnongsan.entity.Role;

import java.util.List;

public interface IRoleService {
    List<Role> findAll();
    List<Role> findByNameLike(String name);
    Role findByName(String name);
    Role findById(String id);

    List<Role> findAllByName(String name);

    Role save(Role role);
    void delete(Role role);



    void removePermissionsFromRole(Role role, Permission permission);
}
