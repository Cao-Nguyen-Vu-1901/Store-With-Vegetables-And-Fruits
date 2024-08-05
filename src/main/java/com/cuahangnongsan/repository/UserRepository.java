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


    User findByUsernameAndStatus(String username, String status);
    List<User> findAllByNameLike(String name);
    List<User> findAllByDob(LocalDate date);
    List<User> findAllByGender(String gender);
    List<User> findAllByEmailLike(String email);
    List<User> findAllByPhoneNumberLike(String phoneNumber);
    List<User> findAllByAddressLike(String address);
    List<User> findAllByStatus(String status);

    @Query("select u from User u where u.roles <> :role")
    List<User> findAllByRoleEqualUser(@Param("role") List<Role> role);


}
