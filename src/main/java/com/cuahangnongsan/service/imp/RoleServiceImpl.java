package com.cuahangnongsan.service.imp;

import com.cuahangnongsan.entity.Role;
import com.cuahangnongsan.repository.RoleRepository;
import com.cuahangnongsan.service.IRoleService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleServiceImpl implements IRoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public List<Role> findAllByName(String name) {
        return roleRepository.findAllByName(name);
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
}
