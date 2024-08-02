package com.cuahangnongsan.service;

import com.cuahangnongsan.entity.User;

public interface IUserService {


    User findById(String id);
    User findByUsername(String username);

    User save(User user);
    void removeSessionMessage();
}
