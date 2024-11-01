package com.bookstore.respository;

import com.bookstore.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("select a from Author a where a.fullName = ?1")
    Optional<Author> findAuthorByFullName(String name);

    @Query("select a from Author a")
    Page<Author> getAllAuthor(Pageable pageable);

    @Query("select a from Author a")
    List<Author> getListAuthor();
}
