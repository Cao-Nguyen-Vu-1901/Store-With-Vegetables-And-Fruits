package com.cuahangnongsan.firebase;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private String username;
    private String email;

    public User() {
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }


}
