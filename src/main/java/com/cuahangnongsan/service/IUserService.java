package com.cuahangnongsan.service;

import com.cuahangnongsan.entity.User;

import java.time.LocalDate;
import java.util.List;

public interface IUserService {


    User findById(String id);

    User findByUsername(String username);

    User save(User user);

    void removeSessionMessage();


    List<User> findAll();

    List<User> findAllByNameLike(String name);



    List<User> findAllByEmailLike(String email);

    List<User> findAllByPhoneNumberLike(String phoneNumber);

    List<User> findAllByAddressLike(String address);

    List<User> findAllByStatus(boolean status);

    List<User> findAllByRoleName(String name);


}
