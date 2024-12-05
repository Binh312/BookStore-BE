package com.bookstore.respository;

import com.bookstore.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select c from Category c where c.name = ?1")
    Optional<Category> findCategoriesByName(String Name);

    boolean existsByName(String name);

    @Query("select c from Category c where c.name like %?1%")
    Page<Category> searchCategory(String name, Pageable pageable);

    @Query("select c from Category c")
    Page<Category> getAllCategory(Pageable pageable);

    @Query("select c from Category c")
    List<Category> getListCategory();
}
