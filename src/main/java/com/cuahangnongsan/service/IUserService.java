package com.cuahangnongsan.service;

import com.cuahangnongsan.entity.Role;
import com.cuahangnongsan.entity.User;

import java.time.LocalDate;
import java.util.List;

public interface IUserService {


    User findById(String id);

    User findByUsername(String username);

    User save(User user);

    void removeSessionMessage();

    void delete(User user);

    List<User> findAll();

    List<User> findAllByNameLike(String name);

    List<User> findAllByDob(LocalDate date);

    List<User> findAllByGender(String gender);

    List<User> findAllByEmailLike(String email);

    List<User> findAllByPhoneNumberLike(String phoneNumber);

    List<User> findAllByAddressLike(String address);

    List<User> findAllByStatus(String status);

    List<User> findAllByRoleEqualUser(List<Role> role);


}
