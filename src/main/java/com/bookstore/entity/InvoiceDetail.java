package com.bookstore.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "invoice_detail")
@Getter
@Setter
public class InvoiceDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    private Integer price;

    @ManyToOne
    private Book book;

    @ManyToOne
    private Invoice invoice;
}
