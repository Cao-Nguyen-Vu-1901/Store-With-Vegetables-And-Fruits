package com.cuahangnongsan.dto.response;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class UserResponse  {

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

    Set<RoleResponse> roles;



}
