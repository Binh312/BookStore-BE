package com.bookstore.api;

import com.bookstore.entity.Publisher;
import com.bookstore.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/publisher")
@CrossOrigin("*")
public class PublisherApi {

    @Autowired
    private PublisherService publisherService;

    @PostMapping("/create-publisher")
    public ResponseEntity<?> createPublisher(@RequestBody Publisher publisher){
        Publisher result = publisherService.createPublisher(publisher);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
