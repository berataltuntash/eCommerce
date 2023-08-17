package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity(name="orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="user_id")
    private int user_id;
    @Column(name="order_id")
    private int order_id;
    @Column(name="product_id")
    private int product_id;
    @Column(name="quantity")
    private int quantity;
    @Column(name="price")
    private int price;
    @Column(name="date")
    private String date;
}
