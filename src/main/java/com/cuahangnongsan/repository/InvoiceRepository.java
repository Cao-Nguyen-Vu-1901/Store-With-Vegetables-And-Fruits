package com.cuahangnongsan.repository;

import com.cuahangnongsan.entity.Invoice;
import com.cuahangnongsan.entity.Product;
import com.cuahangnongsan.entity.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice,String> {
    List<Invoice> findAllByStatusAndUser(String status, User user);
    List<Invoice> findAllByUser(User user);
    List<Invoice> findAllByOrderDate(LocalDate date);
    List<Invoice> findAllByStatus(String status);
    List<Invoice> findAllByAddressLike(String address);
    List<Invoice> findAllByCancelDate(LocalDate date);
    @Query("select u from Invoice u where u.user.name like :name")
    List<Invoice> findAllByUserNameLike(@Param("name")String name);

    @Query("select u from Invoice u where u.user.phoneNumber like :phoneNumber")
    List<Invoice> findAllByPhoneNumberLike(@Param("phoneNumber")String phoneNumber);




}
