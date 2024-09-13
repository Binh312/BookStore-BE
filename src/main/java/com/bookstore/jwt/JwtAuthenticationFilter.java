package com.bookstore.jwt;

import com.bookstore.dto.CustomUserDetails;
import com.bookstore.entity.User;
import com.bookstore.respository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider tokenProvider;

    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider, UserRepository userRepository) {
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
    }


    /*
    *  lấy jwt từ header khi người dùng gửi yêu cầu
    * */
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        // Kiểm tra xem header Authorization có chứa thông tin jwt không
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            // Lấy jwt từ request
            String jwt = getJwtFromRequest((HttpServletRequest) request);
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                // Lấy id user từ chuỗi jwt
                Long userId = tokenProvider.getUserIdFromJWT(jwt);
                // Lấy thông tin người dùng từ id
                UserDetails userDetails = new CustomUserDetails(userRepository.findById(userId).get());
                System.out.println("user by access token-----: "+userDetails);
                if(userDetails != null) {
                    // Nếu người dùng hợp lệ, set thông tin cho Seturity Context
                    Authentication authentications = getAuthentication(jwt, userId);
                    SecurityContextHolder.getContext().setAuthentication(authentications);
                }
            }
        } catch (Exception ex) {
            log.error("failed on set user authentication", ex);
        }
        filterChain.doFilter(request, response);
    }


    public Authentication getAuthentication(String token, Long userId) {
        User u = userRepository.findById(userId).get();

//        String authol = u.getRole().getName();
        String authol = u.getRole();
        System.out.println("role: "+authol);
        Collection<? extends GrantedAuthority> authorities = Arrays
                .stream(authol.split(","))
                .filter(auth -> !auth.trim().isEmpty())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        org.springframework.security.core.userdetails.User principal = new org.springframework.security.core.userdetails.User(userId.toString(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }
}
