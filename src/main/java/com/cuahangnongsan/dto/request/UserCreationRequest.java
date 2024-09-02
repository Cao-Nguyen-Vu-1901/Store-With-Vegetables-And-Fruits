package com.cuahangnongsan.dto.request;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest implements Serializable {

    String name;

    String email;

    String phoneNumber;

    String username;

    String password;


}
