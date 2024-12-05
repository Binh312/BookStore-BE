package com.bookstore.respository;

import com.bookstore.entity.Publisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    @Query("select p from Publisher p where p.name = ?1")
    Optional<Publisher> findPublisherByName(String name);

    @Query("select p from Publisher p")
    List<Publisher> getListPublisher();

    @Query("select p from Publisher p where p.name like %?1%")
    Page<Publisher> searchPublisher(String name, Pageable pageable);

    @Query("select p from Publisher p")
    Page<Publisher> getAllPublisher(Pageable pageable);
}
