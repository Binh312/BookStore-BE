package com.bookstore.service;

import com.bookstore.entity.Notification;
import com.bookstore.entity.User;
import com.bookstore.exception.GlobalException;
import com.bookstore.respository.NotificationRepository;
import com.bookstore.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    public Notification createNotification(Notification notification){
        Optional<User> user = userRepository.findById(notification.getUser().getId());
        if (user.isEmpty()){
            throw new GlobalException("Người dùng không tồn tại");
        }
        notification.setCreatedDate(LocalDateTime.now());
        notification.setUser(user.get());
        return notificationRepository.save(notification);
    }

    public String deleteNotification(Long id){
        Optional<Notification> notification = notificationRepository.findById(id);
        if (notification.isEmpty()){
            throw new GlobalException("Thông báo không tồn tại");
        }
        notificationRepository.delete(notification.get());
        return "Đã xóa thông báo thành công";
    }

    public Notification findNotification(Long id){
        Optional<Notification> notification = notificationRepository.findById(id);
        if (notification.isEmpty()){
            throw new GlobalException("Thông báo không tồn tại");
        }
        return notification.get();
    }
}
