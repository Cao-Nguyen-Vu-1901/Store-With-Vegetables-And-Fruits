package com.cuahangnongsan.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
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

    LocalDate dob;

    String gender;

    String email;

    @Column(name = "phonenumber")
    String phoneNumber;

    String image;

    String username;

    String password;

    String address;

    boolean status;


    @ManyToMany(fetch = FetchType.EAGER)
    Set<Role> roles;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    List<Invoice> invoice ;

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", dob=" + dob +
                ", gender='" + gender + '\'' +
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
