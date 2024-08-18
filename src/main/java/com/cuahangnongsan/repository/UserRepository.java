package com.cuahangnongsan.repository;

import com.cuahangnongsan.entity.Role;
import com.cuahangnongsan.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User,String> {


    User findByUsernameAndStatus(String username, boolean status);
    User findByUsername(String username);
    List<User> findAllByNameLike(String name);
    List<User> findAllByEmailLike(String email);
    List<User> findAllByPhoneNumberLike(String phoneNumber);
    List<User> findAllByAddressLike(String address);
    List<User> findAllByStatus(boolean status);

    @Query("select u from User  u join u.roles r where u.createDate = :date and r.name = :name ")
    List<User> findAllByCreateDateAndRoleName(@Param("date") LocalDate date, @Param("name") String name);

    @Query("select user from User user join user.roles role where role.name like %:name%")
    List<User> findAllByRoleName(@Param("name") String name);

    @Query("select u from User  u join u.roles r where " +
            "EXTRACT(month from u.createDate) = :month " +
            "and EXTRACT(year from  u.createDate) = :year and r.name = :name")
    List<User> findAllByMothYearRoleName(@Param("month") int month, @Param("year") int year ,@Param("name") String name);


    Page<User> findAllByNameLike(String name, Pageable pageable);
    Page<User> findAllByEmailLike(String email, Pageable pageable);
    Page<User> findAllByAddressLike(String address, Pageable pageable);
    Page<User> findAllByStatus(boolean status, Pageable pageable);
    Page<User> findAllByRoles(Set<Role> roles, Pageable pageable);




}
