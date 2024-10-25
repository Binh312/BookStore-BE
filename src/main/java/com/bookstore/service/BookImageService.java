package com.bookstore.service;

import com.bookstore.entity.BookImage;
import com.bookstore.exception.GlobalException;
import com.bookstore.respository.BookImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookImageService {

    @Autowired
    private BookImageRepository bookImageRepository;

    public String deleteBookImage(Long id){
        Optional<BookImage> bookImage = bookImageRepository.findBookImageById(id);
        if (bookImage.isEmpty()){
            throw new GlobalException("Ảnh sách này không tồn tại");
        }
        bookImageRepository.delete(bookImage.get());
        return "Đã xóa ảnh sách thành công";
    }
}
