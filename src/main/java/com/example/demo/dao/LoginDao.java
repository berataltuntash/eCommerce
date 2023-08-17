package com.example.demo.dao;

import com.example.demo.entity.Cart;
import com.example.demo.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginDao extends JpaRepository<Login, Integer> {
}
