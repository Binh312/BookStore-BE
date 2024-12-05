package com.bookstore.api;

import com.bookstore.entity.Cart;
import com.bookstore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin("*")
public class CartApi {

    @Autowired
    private CartService cartService;

    @PostMapping("/create-update")
    public ResponseEntity<?> createOrUpdateCart(@RequestParam Long userId,
                                                @RequestParam Long bookId,
                                                @RequestParam Integer quantity){
        Cart result = cartService.createCart(userId,bookId,quantity);
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

    @GetMapping("/get-list-cart")
    public ResponseEntity<?> getListCart(@RequestParam Long userId){
        List<Cart> list = cartService.getListCart(userId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/decrease-quantity")
    public ResponseEntity<?> decreaseQuantity(@RequestParam Long bookId){
        Integer result = cartService.decreaseQuantity(bookId);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PostMapping("/increase-quantity")
    public ResponseEntity<?> increaseAmount(@RequestParam Long bookId){
        Integer result = cartService.increaseQuantity(bookId);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

}
