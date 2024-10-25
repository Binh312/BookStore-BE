package com.bookstore.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;

@Entity
@Table(name = "history_pay")
@Getter
@Setter
public class HistoryPay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private Double totalAmount;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime createdDate;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalDateTime createdTime;

    private String requestId;

    private String orderId;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;
}

