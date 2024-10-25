package com.bookstore.api;

import com.bookstore.entity.Publisher;
import com.bookstore.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publisher")
@CrossOrigin("*")
public class PublisherApi {

    @Autowired
    private PublisherService publisherService;

    @PostMapping("/create-update")
    public ResponseEntity<?> createOrUpdatePublisher(@RequestBody Publisher publisher){
        Publisher result = publisherService.createPublisher(publisher);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deletePublisher(@RequestParam Long id){
        String str = publisherService.deletePublisher(id);
        return new ResponseEntity<>(str, HttpStatus.OK);
    }

    @GetMapping("/find-publisher")
    public ResponseEntity<?> findPublisher(@RequestParam Long id){
        Publisher result = publisherService.findPublisher(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/get-list-publisher")
    public ResponseEntity<?> getListPublisher(){
        List<Publisher> list = publisherService.getListPublisher();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
