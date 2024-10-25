package com.bookstore.api;

import com.bookstore.entity.ImportBook;
import com.bookstore.service.ImportBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/import-book")
@CrossOrigin("*")
public class ImportBookApi {

    @Autowired
    private ImportBookService importBookService;

    @PostMapping("/create")
    public ResponseEntity<?> createImportBook(@RequestBody ImportBook importBook){
        ImportBook result = importBookService.createImportBook(importBook);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteImportBook(@RequestParam Long id){
        String result = importBookService.deleteImportBook(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/get-all-Import-book")
    public ResponseEntity<?> createImportBook(Pageable pageable){
        Page<ImportBook> result = importBookService.getAllImportBook(pageable);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
