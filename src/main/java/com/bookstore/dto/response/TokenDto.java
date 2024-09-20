package com.bookstore.dto.response;

import com.bookstore.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDto {

    private String token;

    private User user;

}
