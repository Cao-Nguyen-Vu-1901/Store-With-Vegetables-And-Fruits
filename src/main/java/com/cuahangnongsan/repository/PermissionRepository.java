package com.cuahangnongsan.repository;

import com.cuahangnongsan.entity.Permission;
import com.cuahangnongsan.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission,String> {
    Permission findByName(String name);

}
