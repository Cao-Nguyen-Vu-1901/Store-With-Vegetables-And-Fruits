package com.cuahangnongsan.test;

import com.cuahangnongsan.entity.Product;
import com.cuahangnongsan.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;


public class Test {

    public static void main(String[] args) {
        LocalDate today = LocalDate.now();
        LocalDate someDate = LocalDate.of(2024, 7, 29); // Ví dụ giá trị LocalDate
        Period period = today.until(someDate);
        System.out.println("Khoảng cách là: " + period.getYears() + " năm, "
                + period.getMonths() + " tháng, "
                + period.getDays() + " ngày.");

        System.out.println("hi" + today.until(someDate).getDays());
    }

}
