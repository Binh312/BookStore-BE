package com.bookstore.service;

import com.bookstore.dto.CustomUserDetails;
import com.bookstore.dto.response.LoginDto;
import com.bookstore.dto.response.TokenDto;
import com.bookstore.entity.User;
import com.bookstore.exception.GlobalException;
import com.bookstore.jwt.JwtTokenProvider;
import com.bookstore.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private PasswordEncoder passwordEncoder;

    public TokenDto login(LoginDto loginDto){
        Optional<User> user = userRepository.findByEmail(loginDto.getEmail());
        if (user.isEmpty()){
            throw new GlobalException("Tài khoản không tồn tại!");
        }
        if (passwordEncoder.matches(loginDto.getPassword(), user.get().getPassword()) == false){
            throw new GlobalException("Tài khoản hoặc mật khẩu không chính xác!");
        }
        if (user.get().getActived() == false){
            throw new GlobalException("Tài khoản đã bị khóa");
        }

        CustomUserDetails customUserDetails = new CustomUserDetails(user.get());
        String token = jwtTokenProvider.generateToken(customUserDetails);
        TokenDto tokenDto = new TokenDto();
        tokenDto.setUser(user.get());
        tokenDto.setToken(token);
        return tokenDto;
    }

    public User regis(User user){
        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
        if (userOptional.isPresent()){
            throw new GlobalException("Tài khoản đã tồn tại", 400);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedDate(LocalDate.now());
        user.setActived(true);
        user.setRole("ROLE_USER");
        User result = userRepository.save(user);
        return result;
    }

    public User findUserById(Long id){
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()){
            throw new GlobalException("User không tồn tại");
        }
        return user.get();
    }
}
