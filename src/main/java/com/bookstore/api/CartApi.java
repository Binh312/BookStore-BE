package com.bookstore.api;

import com.bookstore.entity.Cart;
import com.bookstore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin("*")
public class CartApi {

    @Autowired
    private CartService cartService;

    @PostMapping("/create-update")
    public ResponseEntity<?> createOrUpdateCart(@RequestBody Cart cart){
        Cart result = cartService.createCart(cart);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCart(@RequestParam Long id){
        String str = cartService.deleteCart(id);
        return new ResponseEntity<>(str, HttpStatus.OK);
    }

    @GetMapping("/find-cart")
    public ResponseEntity<?> findCart(@RequestParam Long id){
        Cart result = cartService.findCart(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
