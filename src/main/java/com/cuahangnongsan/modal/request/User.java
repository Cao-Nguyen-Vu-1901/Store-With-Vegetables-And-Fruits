package com.cuahangnongsan.modal.request;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User  implements Serializable {

    String id ;

    String name;

    Date dob;

    String gender;

    String email;

    String phoneNumber;

    String image;

    String username;

    String password;

    String address;

    String status;

    Set<Role> roles;

    List<Invoice> invoice = new ArrayList<>();


}
