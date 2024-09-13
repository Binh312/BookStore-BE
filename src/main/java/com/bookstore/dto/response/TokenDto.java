package com.bookstore.dto.response;

import com.bookstore.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDto {

    private User user;

    private String token;
}
