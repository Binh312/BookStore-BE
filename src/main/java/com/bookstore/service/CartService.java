package com.bookstore.service;

import com.bookstore.entity.Book;
import com.bookstore.entity.Cart;
import com.bookstore.entity.User;
import com.bookstore.exception.GlobalException;
import com.bookstore.respository.BookRepository;
import com.bookstore.respository.CartRepository;
import com.bookstore.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    public Cart createCart(Cart cart){
        if (cart.getId() == null){
            Optional<User> user = userRepository.findById(cart.getUser().getId());
            if (user.isEmpty()){
                throw new GlobalException("Người dùng không tồn tại");
            }
            Optional<Book> book = bookRepository.findById(cart.getBook().getId());
            if (book.isEmpty()){
                throw new GlobalException("Sách không tồn tại");
            }
            cart.setUser(user.get());
            cart.setBook(book.get());
            return cartRepository.save(cart);
        } else {
            return updateCart(cart);
        }
    }

    public Cart updateCart(Cart cart){
        Optional<Cart> cartOptional = cartRepository.findById(cart.getId());
        if (cartOptional.isEmpty()){
            throw new GlobalException("Giỏ hàng không tồn tại");
        }
        Optional<User> user = userRepository.findById(cart.getUser().getId());
        if (user.isEmpty()){
            throw new GlobalException("Người dùng không tồn tại");
        }
        Optional<Book> book = bookRepository.findById(cart.getBook().getId());
        if (book.isEmpty()){
            throw new GlobalException("Sách không tồn tại");
        }
        cart.setUser(user.get());
        cart.setBook(book.get());
        return cartRepository.save(cart);
    }

    public String deleteCart(Long id){
        Optional<Cart> cartOptional = cartRepository.findById(id);
        if (cartOptional.isEmpty()){
            throw new GlobalException("Giỏ hàng không tồn tại");
        }
        cartRepository.delete(cartOptional.get());
        return "Đã xóa giỏ hàng thành công";
    }

    public Cart findCart(Long id){
        Optional<Cart> cartOptional = cartRepository.findById(id);
        if (cartOptional.isEmpty()){
            throw new GlobalException("Giỏ hàng không tồn tại");
        }
        return cartOptional.get();
    }
}
