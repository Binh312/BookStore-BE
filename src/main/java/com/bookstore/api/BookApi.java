package com.bookstore.api;

import com.bookstore.dto.request.BookSearch;
import com.bookstore.entity.Book;
import com.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
@CrossOrigin("*")
public class BookApi {

    @Autowired
    private BookService bookService;

    @PostMapping("/create-update")
    public ResponseEntity<?> createOrUpdateBook(@RequestBody Book book){
        Book result = bookService.createBook(book);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteBook(@RequestParam Long id){
        String str = bookService.deleteBook(id);
        return new ResponseEntity<>(str, HttpStatus.OK);
    }

    @GetMapping("/find-book")
    public ResponseEntity<?> findBook(@RequestParam Long id){
        Book result = bookService.findBook(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/get-all-book")
    public ResponseEntity<?> getAllBook(Pageable pageable){
        Page<Book> page = bookService.getAllBook(pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/find-all-list")
    public ResponseEntity<?> getListBook(){
        List<Book> list = bookService.getListBook();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/search-full")
    public ResponseEntity<?> searchFull(@RequestBody BookSearch s, Pageable pageable){
        Page<Book> list = bookService.findProductsByCriteria(s.getCategoryIds(), s.getAuthorIds(), s.getMinPrice(), s.getMaxPrice(), pageable);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
