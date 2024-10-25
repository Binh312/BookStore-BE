package com.bookstore.respository;

import com.bookstore.entity.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface BookCategoryRepository extends JpaRepository<BookCategory, Long> {

    @Modifying
    @Transactional
    @Query("delete from BookCategory p where p.book.id = ?1")
    int deleteByProduct(Long productId);
}
