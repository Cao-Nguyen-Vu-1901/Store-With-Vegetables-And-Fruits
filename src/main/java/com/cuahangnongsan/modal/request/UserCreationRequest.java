package com.cuahangnongsan.modal.request;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;

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
