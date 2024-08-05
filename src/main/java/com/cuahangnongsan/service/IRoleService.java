package com.cuahangnongsan.service;

import com.cuahangnongsan.entity.Role;

import java.util.List;

public interface IRoleService {
    List<Role> findAllByName(String name);
}
