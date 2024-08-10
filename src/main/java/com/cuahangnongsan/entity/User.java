package com.cuahangnongsan.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id ;

    String name;

    String email;

    @Column(name = "phonenumber")
    String phoneNumber;

    String image;

    String username;

    String password;

    String address;

    boolean status;
    boolean accountVerified;
    int failedLoginAttempts;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id"))
    Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    List<Invoice> invoice = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    List<Post> post = new ArrayList<>();

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", image='" + image + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", status='" + status + '\'' +
                ", roles=" + roles +
                ", invoice=" + invoice +
                '}';
    }
}
