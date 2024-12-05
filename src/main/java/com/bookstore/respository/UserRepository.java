package com.bookstore.respository;

import com.bookstore.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.email = ?1")
    Optional<User> findByEmail(String email);

    @Query("select u from User u where u.email = ?1 and u.password = ?2")
    Optional<User> findByEmailAndPassword(String email, String password);

    @Query("select u from User u")
    Page<User> getAllUser(Pageable pageable);

    @Query("select u from User u where u.role = ?1")
    Page<User> getAllUserByRole(Pageable pageable,String role);

    @Query("select u from User u where u.email like %?1% or u.fullName like %?1%")
    Page<User> searchUser(String name, Pageable pageable);
}
