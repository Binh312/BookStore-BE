package com.bookstore.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "book")
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String image;

    private String description;

    private Long quantity;

    private Date createdDate;

    private Integer price;

    private Integer oldPrice;

    private Integer numberPage;

    private String size;

    private Integer publishYear;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;


}
