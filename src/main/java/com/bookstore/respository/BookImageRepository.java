package com.bookstore.respository;

import com.bookstore.entity.BookImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.Optional;

@Repository
public interface BookImageRepository extends JpaRepository<BookImage, Long> {

    @Query("select bi from BookImage bi where bi.id = ?1")
    Optional<BookImage> findBookImageById(Long id);
}
