package com.cuahangnongsan.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String name;

    @ManyToMany(fetch = FetchType.EAGER)
    Set<Permission> permissions = new HashSet<>();

    @ManyToMany(mappedBy = "roles",fetch = FetchType.EAGER)
    Set<User> users = new HashSet<>();


}
