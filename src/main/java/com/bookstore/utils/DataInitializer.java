package com.bookstore.utils;

import com.bookstore.entity.User;
import com.bookstore.enums.UserType;
import com.bookstore.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {
        String password = "admin";
        String email = "admin@gmail.com";

        // Kiểm tra xem tài khoản đã tồn tại chưa
        if (userRepository.findByEmail(email).isEmpty()) {
            User user = new User();
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setUserType(UserType.EMAIL);
            user.setActived(true);
            user.setCreatedDate(LocalDate.now());
            user.setFullName("ADMIN");
            user.setRole(Contains.ROLE_ADMIN);
            // Nếu cần mã hóa mật khẩu, bạn có thể làm ở đây
            userRepository.save(user);
        }
    }
}
