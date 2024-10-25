package com.bookstore.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "import_book")
@Getter
@Setter
public class ImportBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private Long quantity;

    private Integer price;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate createdDate;

    @ManyToOne
    private Book book;
}
