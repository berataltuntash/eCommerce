package com.example.demo.dao;

import com.example.demo.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartDao extends JpaRepository<Cart, Integer> {
    Cart findByProduct_IdAndUser_Id(int productId, int userId);

}
