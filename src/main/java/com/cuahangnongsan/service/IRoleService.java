package com.cuahangnongsan.service;

import com.cuahangnongsan.entity.Role;

import java.util.List;

public interface IRoleService {
    List<Role> findAll();
    Role findByName(String name);

    void save(Role role);
    void delete(Role role);
}
