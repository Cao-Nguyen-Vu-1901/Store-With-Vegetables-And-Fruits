package com.cuahangnongsan.repository;

import com.cuahangnongsan.entity.Role;
import com.cuahangnongsan.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,String> {


    User findByUsernameAndStatus(String username, boolean status);
    User findByUsername(String username);
    List<User> findAllByNameLike(String name);
    List<User> findAllByEmailLike(String email);
    List<User> findAllByPhoneNumberLike(String phoneNumber);
    List<User> findAllByAddressLike(String address);
    List<User> findAllByStatus(boolean status);

    @Query("select user from User user join user.roles role where role.name like %:name%")
    List<User> findAllByRoleName(@Param("name") String name);


}
