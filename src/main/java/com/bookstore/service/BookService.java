package com.bookstore.service;

import com.bookstore.dto.response.BookSpecification;
import com.bookstore.entity.*;
import com.bookstore.exception.GlobalException;
import com.bookstore.respository.*;
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

    @Autowired
    private BookAuthorRepository bookAuthorRepository;

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
            book.setAmount(1);
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

            for(Long id : book.getListAuthorId()){
                BookAuthor bookAuthor = new BookAuthor();
                bookAuthor.setBook(book);
                Author a = new Author();
                a.setId(id);
                bookAuthor.setAuthor(a);
                bookAuthorRepository.save(bookAuthor);
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

        bookAuthorRepository.deleteBookAuthorByProduct(book.getId());
        for(Long id : book.getListAuthorId()){
            BookAuthor bookAuthor = new BookAuthor();
            bookAuthor.setBook(book);
            Author a = new Author();
            a.setId(id);
            bookAuthor.setAuthor(a);
            bookAuthorRepository.save(bookAuthor);
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
        bookOptional.get().setAmount(1);
        bookRepository.save(bookOptional.get());
        return bookOptional.get();
    }

    public Page<Book> getAllBook(Pageable pageable){
        return bookRepository.getAllBook(pageable);
    }

    public List<Book> getListBook(){
        return bookRepository.findAll();
    }

    public Page<Book> searchBookByTitle(String title, Pageable pageable){
        Page<Book> page = bookRepository.searchBooksByTitle(title,pageable);
        return page;
    }

    public Page<Book> findProductsByCriteria(List<Long> categoryIds, List<Long> authorIds, Integer minPrice, Integer maxPrice, Pageable pageable) {
        BookSpecification spec = new BookSpecification(categoryIds, authorIds, minPrice, maxPrice);
        return bookRepository.findAll(spec, pageable);
    }

    public Integer decreaseAmount(Long bookId){
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.get().getAmount() > 1) {
            book.get().setAmount(book.get().getAmount() - 1);
        }
        bookRepository.save(book.get());
        return book.get().getAmount();
    }

    public Integer increaseAmount(Long bookId){
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.get().getAmount() < book.get().getQuantity()) {
            book.get().setAmount(book.get().getAmount() + 1);
        }
        bookRepository.save(book.get());
        return book.get().getAmount();
    }
}
