package com.cuahangnongsan.dto.response;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleResponse {
    @Id
    String name;

    String description;

    Set<PermissionResponse> permissions;
}

