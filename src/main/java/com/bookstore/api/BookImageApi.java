package com.bookstore.api;

import com.bookstore.service.BookImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/book-image")
@CrossOrigin("*")
public class BookImageApi {

    @Autowired
    private BookImageService bookImageService;

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteBookImage(@RequestParam Long id){
        String str = bookImageService.deleteBookImage(id);
        return new ResponseEntity<>(str, HttpStatus.OK);
    }
}
