package com.cuahangnongsan.modal.request;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
