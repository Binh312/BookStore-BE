package com.bookstore.service;

import com.bookstore.dto.response.BookSpecification;
import com.bookstore.entity.*;
import com.bookstore.exception.GlobalException;
import com.bookstore.respository.BookCategoryRepository;
import com.bookstore.respository.BookImageRepository;
import com.bookstore.respository.BookRepository;
import com.bookstore.respository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private BookImageRepository bookImageRepository;


    @Autowired
    private BookCategoryRepository bookCategoryRepository;

    public Book createBook(Book book){
        if(book.getId() == null){
            Optional<Book> bookOptional = bookRepository.findBookByTitle(book.getTitle());
//            if (bookOptional.isPresent()){
//                throw new GlobalException("Tiêu đề sách đã tồn tại!");
//            }
            Optional<Publisher> publisherOptional = publisherRepository.findById(book.getPublisher().getId());
            if (publisherOptional.isEmpty()){
                throw new GlobalException("Nhà xuất bản không tồn tại");
            }
            book.setPublisher(publisherOptional.get());
            book.setCreatedDate(LocalDate.now());
            bookRepository.save(book);
            for(String s : book.getListLink()){
                BookImage bookImage = new BookImage();
                bookImage.setBook(book);
                bookImage.setImage(s);
                bookImageRepository.save(bookImage);
            }

            for(Long id : book.getListCategoryId()){
                BookCategory bookCategory = new BookCategory();
                bookCategory.setBook(book);
                Category c = new Category();
                c.setId(id);
                bookCategory.setCategory(c);
                bookCategoryRepository.save(bookCategory);
            }
            return book;
        }
        else {
            return updateBook(book);
        }
    }

    public Book updateBook(Book book){
        Optional<Book> bookOptional = bookRepository.findById(book.getId());
        if (bookOptional.isEmpty()){
            throw new GlobalException("Sách không tồn tại");
        }
//        if (bookOptional.get().getTitle().equals(book.getTitle())){
//            throw new GlobalException("Tiêu đề sách đã tồn tại!");
//        }
        Optional<Publisher> publisherOptional = publisherRepository.findById(book.getPublisher().getId());
        if (publisherOptional.isEmpty()){
            throw new GlobalException("Nhà xuất bản không tồn tại");
        }
        book.setPublisher(publisherOptional.get());
        book.setCreatedDate(LocalDate.now());
        bookRepository.save(book);

        for(String s : book.getListLink()){
            BookImage bookImage = new BookImage();
            bookImage.setBook(book);
            bookImage.setImage(s);
            bookImageRepository.save(bookImage);
        }

        bookCategoryRepository.deleteByProduct(book.getId());
        for(Long id : book.getListCategoryId()){
            BookCategory bookCategory = new BookCategory();
            bookCategory.setBook(book);
            Category c = new Category();
            c.setId(id);
            bookCategory.setCategory(c);
            bookCategoryRepository.save(bookCategory);
        }
        return book;
    }

    public String deleteBook(Long id){
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isEmpty()){
            throw new GlobalException("Sách không tồn tại");
        }
        bookRepository.delete(bookOptional.get());
        return "Đã xóa sách thành công";
    }

    public Book findBook(Long id){
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isEmpty()){
            throw new GlobalException("Sách không tồn tại");
        }
        return bookOptional.get();
    }

    public Page<Book> getAllBook(Pageable pageable){
        return bookRepository.getAllBook(pageable);
    }

    public List<Book> getListBook(){
        return bookRepository.findAll();
    }

    public Page<Book> findProductsByCriteria(List<Long> categoryIds, List<Long> authorIds, Integer minPrice, Integer maxPrice, Pageable pageable) {
        BookSpecification spec = new BookSpecification(categoryIds, authorIds, minPrice, maxPrice);
        return bookRepository.findAll(spec, pageable);
    }
}
