package com.bookstore.service;

import com.bookstore.entity.Book;
import com.bookstore.entity.Publisher;
import com.bookstore.exception.GlobalException;
import com.bookstore.respository.BookRepository;
import com.bookstore.respository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    public Book createBook(Book book){
        Optional<Book> bookOptional = bookRepository.findById(book.getId());
        if (bookOptional.get().getTitle().equals(book.getTitle())){
            throw new GlobalException("Tiêu đề sách đã tồn tại!");
        }
        Optional<Publisher> publisherOptional = publisherRepository.findById(book.getPublisher().getId());
        if (publisherOptional.isEmpty()){
            throw new GlobalException("Nhà xuất bản không tồn tại");
        }
        book.setCreatedDate(LocalDate.now());
        return bookRepository.save(book);
    }
}
