package com.bookstore.api;

import com.bookstore.entity.Comment;
import com.bookstore.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
@CrossOrigin("*")
public class CommentApi {

    @Autowired
    private CommentService commentService;

    @PostMapping("/create-update")
    public ResponseEntity<?> createOrUpdateComment(@RequestBody Comment comment){
        Comment result = commentService.createComment(comment);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteComment(@RequestParam Long id){
        String str = commentService.deleteComment(id);
        return new ResponseEntity<>(str, HttpStatus.OK);
    }

    @GetMapping("/find-comment")
    public ResponseEntity<?> findComment(@RequestParam Long id){
        Comment result = commentService.findComment(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
