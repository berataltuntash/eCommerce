package com.example.demo.controller;
import com.example.demo.entity.Cart;
import com.example.demo.entity.Order;
import com.example.demo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.demo.service.OrderService;

@RestController
@RequestMapping("/api/order")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {
    private final OrderService orderService;
    private final CartService cartService;


    @Autowired
    public OrderController(OrderService orderService, CartService cartService) {
        this.orderService = orderService;
        this.cartService = cartService;
    }
    @PostMapping
    public ResponseEntity<Void> addOrder(@RequestBody int userid) {
        orderService.saveOrder(userid);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
