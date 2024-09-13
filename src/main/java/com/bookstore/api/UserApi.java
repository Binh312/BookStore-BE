package com.bookstore.api;

import com.bookstore.dto.response.LoginDto;
import com.bookstore.dto.response.TokenDto;
import com.bookstore.entity.User;
import com.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class UserApi {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){
        TokenDto tokenDto = userService.login(loginDto);
        return new ResponseEntity<>(tokenDto, HttpStatus.OK);
    }

    @PostMapping("/regis")
    public ResponseEntity<?> regis(@RequestBody User user){
        User result = userService.regis(user);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/admin/check")
    public void checkAdmin(){

    }
}
