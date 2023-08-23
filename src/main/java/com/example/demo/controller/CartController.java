package com.example.demo.controller;

import com.example.demo.Requests.CartRequest;
import com.example.demo.entity.Cart;
import com.example.demo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<Cart>> getUsersCart(Integer user_id) {
        List<Cart> cart = cartService.getUsersCart(user_id);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<Void> addToCart(@RequestBody CartRequest cartRequest) {
        cartService.addToCart(cartRequest.getProductId(), cartRequest.getUserId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }



    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteFromCart(@RequestBody CartRequest cartRequest) {
        cartService.deleteFromCart(cartRequest.getProductId(), cartRequest.getUserId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/decrement")
    public ResponseEntity<Void> decrementFromCart(@RequestBody CartRequest cartRequest) {
        cartService.decrementFromCart(cartRequest.getProductId(), cartRequest.getUserId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
