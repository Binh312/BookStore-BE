package com.bookstore.api;

import com.bookstore.entity.Notification;
import com.bookstore.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notification")
@CrossOrigin("*")
public class NotificationApi {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/create")
    public ResponseEntity<?> createNotification(@RequestBody Notification notification){
        Notification result = notificationService.createNotification(notification);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteNotification(@RequestParam Long id){
        String str = notificationService.deleteNotification(id);
        return new ResponseEntity<>(str, HttpStatus.OK);
    }

    @GetMapping("/find-notification")
    public ResponseEntity<?> findNotification(@RequestParam Long id){
        Notification result = notificationService.findNotification(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
