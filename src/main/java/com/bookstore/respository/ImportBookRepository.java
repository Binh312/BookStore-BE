package com.bookstore.respository;

import com.bookstore.entity.ImportBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportBookRepository extends JpaRepository<ImportBook, Long> {

    @Override
    Page<ImportBook> findAll(Pageable pageable);
}
