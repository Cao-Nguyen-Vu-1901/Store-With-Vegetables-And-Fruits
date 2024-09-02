package com.cuahangnongsan.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleResponse {

    String id;
    String name;
    Set<PermissionResponse> permissions = new HashSet<>();



}
