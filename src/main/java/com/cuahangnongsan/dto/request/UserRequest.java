package com.cuahangnongsan.dto.request;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest implements Serializable {

    String name;

    String email;

    String phoneNumber;

    String image;

    String username;

    String password;

    String address;

}
