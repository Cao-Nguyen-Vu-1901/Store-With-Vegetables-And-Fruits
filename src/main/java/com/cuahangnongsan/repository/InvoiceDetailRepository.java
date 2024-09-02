package com.cuahangnongsan.repository;

import com.cuahangnongsan.entity.Invoice;
import com.cuahangnongsan.entity.InvoiceDetail;
import com.cuahangnongsan.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail,String> {
    List<InvoiceDetail> findAllByInvoice(Invoice invoice);
    List<InvoiceDetail> findAllByInvoiceUpdateDate(LocalDate date);

//    @Query("select id from InvoiceDetail id join Invoice i where extract(month from i.updateDate) = :month" +
//            " and extract(year from i.updateDate) = :year and i.status = :status ")
//    List<InvoiceDetail> findAllByInvoiceUpdateDate_MonthValueAndInvoice_UpdateDate_YearAndInvoiceStatus(@Param("month") int month, @Param("year") int year,
//                                                  @Param("status") String status);
//    List<InvoiceDetail> findAllByInvoiceUpdateDateMonthValueAndInvoiceUpdateDateYearAndInvoiceStatus(int month,int year,
//                                                  String status);
}
