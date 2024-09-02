package com.cuahangnongsan.repository;

import com.cuahangnongsan.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role,String> {
    List<Role> findAllByName(String name);
    Role findByName(String name);
}
