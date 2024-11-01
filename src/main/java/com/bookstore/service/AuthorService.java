package com.bookstore.service;

import com.bookstore.entity.Author;
import com.bookstore.exception.GlobalException;
import com.bookstore.respository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public Author createAuthor(Author author){
        if (author.getId() == null){
            Optional<Author> authorOptional = authorRepository.findAuthorByFullName(author.getFullName());
            if (authorOptional.isPresent()){
                throw new GlobalException("Tên tác giả đã tồn tại");
            }
            return authorRepository.save(author);
        } else {
            return updateAuthor(author);
        }
    }

    public Author updateAuthor(Author author){
        Optional<Author> authorOptional = authorRepository.findById(author.getId());
        if (authorOptional.isEmpty()){
            throw new GlobalException("Tác giả không tồn tại");
        }
        if (authorOptional.get().getFullName().equals(author.getFullName())){
            throw new GlobalException("Tên tác giả đã tồn tại");
        }
        return authorRepository.save(author);
    }

    public String deleteAuthor(Long id){
        Optional<Author> authorOptional = authorRepository.findById(id);
        if (authorOptional.isEmpty()){
            throw new GlobalException("Tác giả không tồn tại");
        }
        authorRepository.delete(authorOptional.get());
        return "Đã xóa tác giả thành công";
    }

    public Author findAuthor(Long id){
        Optional<Author> authorOptional = authorRepository.findById(id);
        if (authorOptional.isEmpty()){
            throw new GlobalException("Tác giả không tồn tại");
        }
        return authorOptional.get();
    }

    public Page<Author> getAllAuthor(Pageable pageable){
        return authorRepository.getAllAuthor(pageable);
    }

    public List<Author> getListAuthor(){
        return authorRepository.getListAuthor();
    }
}
