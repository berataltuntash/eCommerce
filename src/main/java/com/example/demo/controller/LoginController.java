package com.example.demo.controller;

import com.example.demo.Requests.LoginRequest;
import com.example.demo.Requests.SignupRequest;
import com.example.demo.entity.Cart;
import com.example.demo.service.CartService;
import com.example.demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {
    private final LoginService loginService;
    private final CartService cartService;

    @Autowired
    public LoginController(LoginService loginService, CartService cartService) {
        this.loginService = loginService;
        this.cartService = cartService;
    }


    @PostMapping
    public ResponseEntity<Boolean> setLogin(@RequestBody SignupRequest signupRequest){
        Boolean theboolean = loginService.setLogin(signupRequest.getName(), signupRequest.getSurname(), signupRequest.getPassword(), signupRequest.getEmail());
        return new ResponseEntity<>(theboolean,HttpStatus.CREATED);
    }

    @PostMapping("/check-login")
    public ResponseEntity<Integer> checkLogin(@RequestBody LoginRequest loginRequest){
        Boolean myboolean = loginService.checkLogin(loginRequest.getEmail(), loginRequest.getPassword());
        if (myboolean){
            int user_id = loginService.getUserID(loginRequest.getEmail());
            cartService.moveCart(user_id);
            return new ResponseEntity<>(user_id,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
