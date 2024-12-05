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

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    public Cart createCart(Long userId, Long bookId, Integer quantity){
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()){
            throw new GlobalException("Người dùng không tồn tại!");
        }
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isEmpty()){
            throw new GlobalException("Sách không tồn tại!");
        }

        Optional<Cart> cartOptional = cartRepository.getCartByUserAndBook(userId,bookId);
        if (cartOptional.isPresent()){
            cartOptional.get().setQuantity(cartOptional.get().getQuantity() + quantity);
            cartOptional.get().setTotalPrice(cartOptional.get().getQuantity() * book.get().getPrice());
            return cartRepository.save(cartOptional.get());
        } else {
            Cart newCart = new Cart();
            newCart.setUser(user.get());
            newCart.setBook(book.get());
            newCart.setQuantity(quantity);
            newCart.setTotalPrice(newCart.getQuantity() * book.get().getPrice());
            return cartRepository.save(newCart);
        }
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

    public List<Cart> getListCart(Long userId){
        return cartRepository.getCartsByUserId(userId);
    }

    public Integer decreaseQuantity(Long bookId){
        Optional<Cart> cart = cartRepository.getCartByBookId(bookId);
        if (cart.get().getQuantity() > 1) {
            cart.get().setQuantity(cart.get().getQuantity() - 1);
        }
        cartRepository.save(cart.get());
        return cart.get().getQuantity();
    }

    public Integer increaseQuantity(Long bookId){
        Optional<Cart> cart = cartRepository.getCartByBookId(bookId);
        if (cart.get().getQuantity() < cart.get().getBook().getQuantity()) {
            cart.get().setQuantity(cart.get().getQuantity() + 1);
        }
        cartRepository.save(cart.get());
        return cart.get().getQuantity();
    }

}
