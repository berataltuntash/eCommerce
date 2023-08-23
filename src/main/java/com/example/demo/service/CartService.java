package com.example.demo.service;

import com.example.demo.dao.CartDao;
import com.example.demo.entity.Cart;
import com.example.demo.entity.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    private final CartDao cartDao;
    private final ProductService productService;
    private final LoginService loginService;

    @Autowired
    public CartService(CartDao cartDao, ProductService productService, LoginService loginService) {
        this.cartDao = cartDao;
        this.productService = productService;
        this.loginService = loginService;
    }

    public void addToCart(int productId, int userId) {
        if (userId == 0){
            return;
        }
        List<Cart> cartList = cartDao.findAll();
        Cart existingCartItem = findCartItemByProductAndUser(productId, userId);

        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
            cartDao.save(existingCartItem);
        } else {
            Cart newCart = new Cart();
            newCart.setProduct(productService.getProductById(productId));
            newCart.setUser(loginService.getLoginById(userId));
            newCart.setQuantity(1);
            cartDao.save(newCart);
        }
    }

    private Cart findCartItemByProductAndUser(int productId, int userId) {
        return cartDao.findByProduct_IdAndUser_Id(productId, userId);
    }

    public void decrementFromCart(int productId, int userId) {
        Cart existingCartItem = findCartItemByProductAndUser(productId, userId);

        if (existingCartItem != null) {
            if (existingCartItem.getQuantity() == 1) {
                cartDao.deleteById(existingCartItem.getId());
            } else {
                existingCartItem.setQuantity(existingCartItem.getQuantity() - 1);
                cartDao.save(existingCartItem);
            }
        }
    }

    public void deleteFromCart(int productId, int userId) {
        Cart existingCartItem = findCartItemByProductAndUser(productId, userId);

        if (existingCartItem != null) {
            cartDao.deleteById(existingCartItem.getId());
        }
    }

    public List<Cart> getUsersCart(Integer userId) {
        if (userId == 0){
            return new ArrayList<>();
        }
        Login user = loginService.getLoginById(userId);
        List<Cart> allCarts = cartDao.findAll();
        List<Cart> usersCart = new ArrayList<>();

        for (Cart cart : allCarts) {
            if (cart.getUser().equals(user)) {
                usersCart.add(cart);
            }
        }

        return usersCart;
    }

    public void deleteUsersCart(Integer userId) {
        List<Cart> userCartItems = getUsersCart(userId);
        cartDao.deleteAll(userCartItems);
    }

    public void moveCart(int userId) {
        List<Cart> cartList = cartDao.findAll();
        Login user = loginService.getLoginById(userId);

        for (Cart cartItem : cartList) {
            if (cartItem.getUser() == null) {
                cartItem.setUser(user);
                cartDao.save(cartItem);
            }
        }
    }
    public Login getUserById(Integer userId) {
        return loginService.getLoginById(userId);
    }
}
