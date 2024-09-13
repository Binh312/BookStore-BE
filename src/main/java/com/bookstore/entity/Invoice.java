package com.bookstore.entity;

import com.bookstore.enums.InvoiceStatus;
import com.bookstore.enums.PayType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "invoice")
@Getter
@Setter
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createDate;

    private Integer totalAmount;

    private String contact;

    private String address;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus invoiceStatus;

    private PayType payType;

    @ManyToOne
    private User user;

    @ManyToOne
    private Voucher voucher;
}
