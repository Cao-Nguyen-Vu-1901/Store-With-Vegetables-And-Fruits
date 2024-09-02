package com.cuahangnongsan.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;



import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id ;


    String name;

    BigDecimal price;

    BigDecimal discountPrice;

    String unit;

    int quantity;


    int remaningQuantity;

    String image;

    @Column(columnDefinition = "TEXT")
    String description;

    LocalDate createdDate;

    LocalDate modifiedDate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;

    @OneToMany(mappedBy = "product")
    List<InvoiceDetail> invoiceDetail = new ArrayList<>();


    @JsonManagedReference(value = "product_json")
    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    List<Comment> comments = new ArrayList<>();

}
