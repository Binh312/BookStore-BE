package com.bookstore.respository;

import com.bookstore.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    @Query("select p from Publisher p where p.name = ?1")
    Optional<Publisher> findPublisherByName(String name);
}
