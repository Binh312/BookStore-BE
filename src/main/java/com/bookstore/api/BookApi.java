package com.bookstore.api;

import com.bookstore.entity.Book;
import com.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/book")
@CrossOrigin("*")
public class BookApi {

    @Autowired
    private BookService bookService;

    @PostMapping("/create-book")
    public ResponseEntity<?> createBook(@RequestBody Book book){
        Book result = bookService.createBook(book);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
