package com.bookstore.respository;

import com.bookstore.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("select c from Cart c where c.user.id = ?1 ")
    List<Cart> getCartsByUserId(Long userId);

    @Query("select c from Cart c where c.user.id = ?1 and c.book.id = ?2")
    Optional<Cart> getCartByUserAndBook(Long userId, Long bookId);

    @Query("select c from Cart c where c.book.id = ?1")
    Optional<Cart> getCartByBookId(Long bookId);
}
