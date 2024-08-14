package com.cuahangnongsan.dto.response;

import com.cuahangnongsan.entity.Permission;
import com.cuahangnongsan.entity.User;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleResponse {

    String id;
    String name;
    Set<Permission> permissions = new HashSet<>();

    Set<User> users = new HashSet<>();
}

