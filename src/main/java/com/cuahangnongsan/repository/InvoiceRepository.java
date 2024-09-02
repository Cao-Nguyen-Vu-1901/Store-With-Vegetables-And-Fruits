package com.cuahangnongsan.repository;

import com.cuahangnongsan.entity.Invoice;
import com.cuahangnongsan.entity.Product;
import com.cuahangnongsan.entity.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice,String> {


    Page<Invoice> findAllByUpdateDateBefore(LocalDate updateDate, Pageable pageable);
    Page<Invoice> findAllByOrderDateBefore(LocalDate orderDate, Pageable pageable);
    Page<Invoice> findAllByStatus(String status, Pageable pageable);
    Page<Invoice> findAllByAddressLike(String address, Pageable pageable);
    Page<Invoice> findAllByUserNameLike(String username, Pageable pageable);
    Page<Invoice> findAllByUserPhoneNumberLike(String phoneNumber, Pageable pageable);

    List<Invoice> findAllByStatusAndUser(String status, User user);
    List<Invoice> findAllByUser(User user);

    @Query("select i from Invoice i where EXTRACT (month from i.updateDate) = :month and EXTRACT (YEAR from i.updateDate) = :year and i.status = :status")
    List<Invoice> findByMonthYearAndStatus(@Param("month") int month, @Param("year") int year, String status);
    List<Invoice> findByUpdateDateAndStatus(LocalDate date, String status);
    List<Invoice> findAllByOrderDate(LocalDate date);
    List<Invoice> findAllByStatus(String status);
    List<Invoice> findAllByAddressLike(String address);
    List<Invoice> findAllByUpdateDate(LocalDate date);
    @Query("select u from Invoice u where u.user.name like :name")
    List<Invoice> findAllByUserNameLike(@Param("name")String name);

    @Query("select u from Invoice u where u.user.phoneNumber like :phoneNumber")
    List<Invoice> findAllByPhoneNumberLike(@Param("phoneNumber")String phoneNumber);




}
