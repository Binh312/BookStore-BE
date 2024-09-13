package com.bookstore.entity;

import com.bookstore.enums.InvoiceStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "status_invoice")
@Getter
@Setter
public class StatusInvoice {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdDate;

    private InvoiceStatus invoiceStatus;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User createdBy;
}