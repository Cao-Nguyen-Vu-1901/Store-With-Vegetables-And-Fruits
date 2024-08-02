package com.cuahangnongsan.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Invoice  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id ;

    String status;

    String address;

    LocalDate orderDate;

    LocalDate cancelDate;

    @OneToMany(mappedBy = "invoice",fetch = FetchType.EAGER)
    List<InvoiceDetail> invoiceDetails;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

}
