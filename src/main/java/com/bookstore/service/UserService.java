package com.bookstore.service;

import com.bookstore.dto.CustomUserDetails;
import com.bookstore.dto.response.LoginDto;
import com.bookstore.dto.response.TokenDto;
import com.bookstore.entity.User;
import com.bookstore.enums.UserType;
import com.bookstore.exception.GlobalException;
import com.bookstore.jwt.JwtTokenProvider;
import com.bookstore.respository.UserRepository;
import com.bookstore.utils.Contains;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    public TokenDto login(LoginDto loginDto){
        Optional<User> user = userRepository.findByEmail(loginDto.getEmail());
        if (user.isEmpty()){
            throw new GlobalException("Tài khoản không tồn tại!");
        }
        if (passwordEncoder.matches(loginDto.getPassword(), user.get().getPassword()) == false){
            throw new GlobalException("Tài khoản hoặc mật khẩu không chính xác!");
        }
        if (user.get().getActived() == false && user.get().getActivationKey()!=null){
            throw new GlobalException("Tài khoản chưa được kích hoạt",444);
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

    public TokenDto loginWithGoogle(GoogleIdToken.Payload payload) {
        User user = new User();
        user.setEmail(payload.getEmail());
        user.setAvatar(payload.get("picture").toString());
        user.setFullName(payload.get("name").toString());
        user.setActived(true);
        user.setRole(Contains.ROLE_USER);
        user.setCreatedDate(LocalDate.now());
        user.setUserType(UserType.GOOGLE);

        Optional<User> users = userRepository.findByEmail(user.getEmail());

        // check infor user
        if (users.isPresent()) {
            if (users.get().getActived() == false) {
                throw new GlobalException("Tài khoản đã bị khóa");
            }
            CustomUserDetails customUserDetails = new CustomUserDetails(users.get());
            String token = jwtTokenProvider.generateToken(customUserDetails);
            TokenDto tokenDto = new TokenDto();
            tokenDto.setToken(token);
            tokenDto.setUser(users.get());
            return tokenDto;
        } else {
            User u = userRepository.save(user);
            CustomUserDetails customUserDetails = new CustomUserDetails(u);
            String token = jwtTokenProvider.generateToken(customUserDetails);
            TokenDto tokenDto = new TokenDto();
            tokenDto.setToken(token);
            tokenDto.setUser(u);
            return tokenDto;
        }
    }

    public User regis(User user){
        userRepository.findByEmail(user.getEmail())
                .ifPresent(exist->{
                    throw new GlobalException("Tên đăng nhập đã tồn tại", 400);
                });
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedDate(LocalDate.now());
        user.setActived(false);
        user.setRole("ROLE_USER");
        user.setPhoneNumber(user.getPhoneNumber());
        user.setActivationKey(randomKey());
        User result = userRepository.save(user);
        mailService.sendEmail(user.getEmail(),"Xác thực tài khoản",
                "Nhập mã xác nhận bên dưới để kích hoạt tài khoản<br>Mã xác nhận của bạn là: "+user.getActivationKey(),
                false, true);
        return result;
    }

    public String confirmAccount(String activationKey, String email){
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()){
            throw new GlobalException("Email không tồn tại!");
        }
        if (user.get().getActivationKey() == null){
            throw new GlobalException("Mã xác thực không đúng");
        }
        if (!user.get().getActivationKey().equals(activationKey)){
            throw new GlobalException("Mã xác thực không trùng khớp");
        } else {
            user.get().setActived(true);
            user.get().setActivationKey(null);
            userRepository.save(user.get());
            return "Xác nhận tài khoản thành công";
        }
    }

    public User findInfoUser(Long id){
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()){
            throw new GlobalException("User không tồn tại");
        }
        return user.get();
    }

    public String randomKey(){
        String str = "12345667890";
        Integer length = str.length()-1;
        StringBuilder stringBuilder = new StringBuilder("");
        for(int i=0; i<6; i++){
            Integer ran = (int)(Math.random()*length);
            stringBuilder.append(str.charAt(ran));
        }
        return String.valueOf(stringBuilder);
    }

    public Page<User> getAllUser(Pageable pageable, String role){
        if (role == null){
            return userRepository.getAllUser(pageable);
        }
        return userRepository.getAllUserByRole(pageable,role);
    }

    public String lockOrUnlock(Long id){
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()){
            throw new GlobalException("Người dùng không tồn tại");
        }
        if (user.get().getActived() == true){
            user.get().setActived(false);
            userRepository.save(user.get());
            return "Đã khóa tài khoản";
        } else {
            user.get().setActived(true);
            userRepository.save(user.get());
            return "Đã mở khóa tài khoản";
        }
    }

}
