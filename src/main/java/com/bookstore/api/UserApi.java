package com.bookstore.api;

import com.bookstore.dto.response.LoginDto;
import com.bookstore.dto.response.TokenDto;
import com.bookstore.entity.User;
import com.bookstore.exception.GlobalException;
import com.bookstore.service.GoogleOAuth2Service;
import com.bookstore.service.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
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

    @Autowired
    private GoogleOAuth2Service googleOAuth2Service;

    @PostMapping("/login/email")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){
        TokenDto tokenDto = userService.login(loginDto);
        return new ResponseEntity<>(tokenDto, HttpStatus.OK);
    }

    @PostMapping("/login/google")
    public ResponseEntity<?> loginWithGoogle(@RequestBody String credential) throws Exception {
        GoogleIdToken.Payload payload = googleOAuth2Service.verifyToken(credential);
        if(payload == null){
            throw new GlobalException("Đăng nhập thất bại");
        }
        TokenDto tokenDto = userService.loginWithGoogle(payload);
        return new ResponseEntity(tokenDto, HttpStatus.OK);
    }

    @PostMapping("/regis")
    public ResponseEntity<?> regis(@RequestBody User user){
        User result = userService.regis(user);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/confirm")
    public ResponseEntity<?> confirmAccount(@RequestParam String key,
                                            @RequestParam String email){
        userService.confirmAccount(key, email);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/find-user")
    public ResponseEntity<?> findInfoUser(@RequestParam Long id){
        User result = userService.findInfoUser(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
