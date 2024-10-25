package com.bookstore.entity;

import com.bookstore.enums.UserType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String fullName;

    private String avatar;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate createdDate;

    private String phoneNumber;

    private Boolean actived;

    private String activationKey;

    private String rememberKey;

    private UserType userType;

    private String role;


}
