package com.cuahangnongsan.dto.response;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {

    String id ;

    String name;

    String email;

    String phoneNumber;

    String image;

    String username;

    String password;

    String address;

    boolean status;
    boolean accountVerified;
    int failedLoginAttempts;

    Set<RoleResponse> roles = new HashSet<>();

//    @JsonManagedReference(value = "user_invoice_dto")
//    List<InvoiceResponse> invoice = new ArrayList<>();

//    @JsonManagedReference(value = "user_post_dto")
//    List<PostResponse> post = new ArrayList<>();


}
