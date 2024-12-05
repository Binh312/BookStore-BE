package com.bookstore.respository;

import com.bookstore.entity.BookAuthor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface BookAuthorRepository extends JpaRepository<BookAuthor, Long> {

    @Modifying
    @Transactional
    @Query("delete from BookAuthor p where p.book.id = ?1")
    int deleteBookAuthorByProduct(Long productId);
}
