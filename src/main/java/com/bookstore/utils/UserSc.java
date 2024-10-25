package com.bookstore.utils;

import com.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableAsync
@EnableScheduling
public class UserSc {

    @Autowired
    private UserService userService;

//    @Async
//    @Scheduled(fixedDelay = 10000)
//    public void lockUserAuto(){
//        userService.lockOrUnlock(12L);
//    }
}
