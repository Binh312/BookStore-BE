package com.bookstore.service;

import com.bookstore.entity.Book;
import com.bookstore.entity.Comment;
import com.bookstore.entity.User;
import com.bookstore.exception.GlobalException;
import com.bookstore.respository.BookRepository;
import com.bookstore.respository.CommentRepository;
import com.bookstore.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;
    public Comment createComment(Comment comment){
        if (comment.getId() == null){
            Optional<User> user = userRepository.findById(comment.getUser().getId());
            if (user.isEmpty()){
                throw new GlobalException("Người dùng không tồn tại");
            }
            Optional<Book> book = bookRepository.findById(comment.getBook().getId());
            if (book.isEmpty()){
                throw new GlobalException("Sách không tồn tại");
            }
            comment.setCreateDate(LocalDateTime.now());
            comment.setUser(user.get());
            comment.setBook(book.get());
            return commentRepository.save(comment);
        } else {
            return updateComment(comment);
        }
    }

    public Comment updateComment(Comment comment){
        Optional<Comment> commentOptional = commentRepository.findById(comment.getId());
        if (commentOptional.isEmpty()){
            throw new GlobalException("Bình luận không tồn tại");
        }
        Optional<User> user = userRepository.findById(comment.getUser().getId());
        if (user.isEmpty()){
            throw new GlobalException("Người dùng không tồn tại");
        }
        Optional<Book> book = bookRepository.findById(comment.getBook().getId());
        if (book.isEmpty()){
            throw new GlobalException("Sách không tồn tại");
        }
        comment.setCreateDate(LocalDateTime.now());
        comment.setUser(user.get());
        comment.setBook(book.get());
        return commentRepository.save(comment);
    }

    public String deleteComment(Long id){
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (commentOptional.isEmpty()){
            throw new GlobalException("Bình luận không tồn tại");
        }
        commentRepository.delete(commentOptional.get());
        return "Đã xóa bình luận thành công";
    }

    public Comment findComment(Long id){
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (commentOptional.isEmpty()){
            throw new GlobalException("Bình luận không tồn tại");
        }
        return commentOptional.get();
    }
}
