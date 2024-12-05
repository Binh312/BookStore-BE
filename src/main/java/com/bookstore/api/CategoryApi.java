package com.bookstore.api;

import com.bookstore.entity.Category;
import com.bookstore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@CrossOrigin("*")
public class CategoryApi {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create-update")
    public ResponseEntity<?> createOrUpdateCategory(@RequestBody Category category){
        Category result = categoryService.createCategory(category);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCategory(@RequestParam Long id){
        String str = categoryService.deleteCategory(id);
        return new ResponseEntity<>(str, HttpStatus.OK);
    }

    @GetMapping("/find-Category")
    public ResponseEntity<?> findCategory(@RequestParam Long id){
        Category result = categoryService.findCategory(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/search-category")
    public ResponseEntity<?> searchCategory(@RequestParam String name, Pageable pageable){
        Page<Category> page = categoryService.searchCategory(name,pageable);
        return new ResponseEntity<>(page,HttpStatus.OK);
    }

    @GetMapping("/get-all-category")
    public ResponseEntity<?> getAllCategory(Pageable pageable){
        Page<Category> page = categoryService.getAllCategory(pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/get-list-category")
    public ResponseEntity<?> getListCategory(){
        List<Category> list = categoryService.getListCategory();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
