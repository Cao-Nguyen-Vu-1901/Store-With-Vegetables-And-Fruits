package com.cuahangnongsan.service.imp;

import com.cuahangnongsan.entity.Permission;
import com.cuahangnongsan.entity.Role;
import com.cuahangnongsan.repository.RoleRepository;
import com.cuahangnongsan.service.IRoleService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleServiceImpl implements IRoleService {

    @Autowired
    RoleRepository roleRepository;


    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public List<Role> findByNameLike(String name) {
        return roleRepository.findByNameLike(name);
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }



    @Override
    public Role findById(String id) {
        return roleRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Role> findAllByName(String name) {
        return roleRepository.findAllByName(name);
    }

    @PreAuthorize("hasAuthority('AUTHORITY_CREATE_ROLE')")
    @Override
    public Role save(Role role)
    {
        return roleRepository.save(role);
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
