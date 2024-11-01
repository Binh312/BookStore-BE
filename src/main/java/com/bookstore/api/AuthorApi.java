package com.bookstore.api;

import com.bookstore.entity.Author;
import com.bookstore.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/author")
@CrossOrigin("*")
public class AuthorApi {

    @Autowired
    private AuthorService authorService;

    @PostMapping("/create-update")
    public ResponseEntity<?> createOrUpdateAuthor(@RequestBody Author author){
        Author result = authorService.createAuthor(author);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAuthor(@RequestParam Long id){
        String str = authorService.deleteAuthor(id);
        return new ResponseEntity<>(str, HttpStatus.OK);
    }

    @GetMapping("/find-author")
    public ResponseEntity<?> findAuthor(@RequestParam Long id){
        Author result = authorService.findAuthor(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/get-all-author")
    public ResponseEntity<?> getAllAuthor(Pageable pageable){
        Page<Author> page = authorService.getAllAuthor(pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/get-list-author")
    public ResponseEntity<?> getListAuthor(){
        List<Author> list = authorService.getListAuthor();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
