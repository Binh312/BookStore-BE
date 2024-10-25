package com.bookstore.service;

import com.bookstore.entity.Book;
import com.bookstore.entity.ImportBook;
import com.bookstore.exception.GlobalException;
import com.bookstore.respository.BookRepository;
import com.bookstore.respository.ImportBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ImportBookService {

    @Autowired
    private ImportBookRepository importBookRepository;

    @Autowired
    private BookRepository bookRepository;

    public ImportBook createImportBook(ImportBook importBook){
        Optional<Book> book = bookRepository.findById(importBook.getBook().getId());
        if (book.isEmpty()){
            throw new GlobalException("Sách này không tồn tại");
        }
        importBook.setCreatedDate(LocalDate.now());
        book.get().setQuantity(book.get().getQuantity()+ importBook.getQuantity());
        bookRepository.save(book.get());
        importBookRepository.save(importBook);
        return importBook;
    }

    public String deleteImportBook(Long id){
        Optional<ImportBook> importBook = importBookRepository.findById(id);
        if (importBook.isEmpty()){
            throw new GlobalException("Đơn nhập sách không tồn tại");
        }
        if (importBook.get().getBook().getQuantity() - importBook.get().getQuantity() < 0){
            throw new GlobalException("Số lượng sách trong đơn nhập không được nhỏ hơn 0");
        }
        importBookRepository.delete(importBook.get());
        return "Đã xóa đơn nhập sách thành công";
    }

    public Page<ImportBook> getAllImportBook(Pageable pageable){
        Page<ImportBook> page = importBookRepository.findAll(pageable);
        return page;
    }
}
