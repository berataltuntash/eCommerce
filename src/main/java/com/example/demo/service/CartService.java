package com.example.demo.service;

import com.example.demo.dao.CartDao;
import com.example.demo.entity.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    private final CartDao cartDao;
    private final ProductService productService;

    @Autowired
    public CartService(CartDao cartDao, ProductService productService) {
        this.cartDao = cartDao;
        this.productService = productService;
    }

    public void addToCart(int productId, Integer userId){
        List<Cart> cartlist = cartDao.findAll();
        if(cartlist.size() == 0){
            Cart newCart = new Cart();
            newCart.setProduct_id(productId);
            newCart.setUser_id(userId);
            newCart.setQuantity(1);
            newCart.setPrice(productService.getProductById(productId).getPrice());
            cartDao.save(newCart);
        }
        else {
            for (int i = 0; i < cartlist.size(); i++) {
                if (cartlist.get(i).getProduct_id() == productId && cartlist.get(i).getUser_id() == userId) {
                    cartlist.get(i).setQuantity(cartlist.get(i).getQuantity() + 1);
                    cartDao.save(cartlist.get(i));
                    return;
                }

            }
            Cart newCart = new Cart();
            newCart.setProduct_id(productId);
            newCart.setUser_id(userId);
            newCart.setQuantity(1);
            newCart.setPrice(productService.getProductById(productId).getPrice());
            cartDao.save(newCart);
            }
        }

    public void decrementFromCart(int productId, Integer userId){
        List<Cart> cartlist = cartDao.findAll();
        for (int i = 0 ; i<cartlist.size();i++){
            if (cartlist.get(i).getProduct_id() == productId && cartlist.get(i).getUser_id() == userId){
                if (cartlist.get(i).getQuantity() == 1){
                    cartDao.deleteById(cartlist.get(i).getId());
                }
                else{
                    cartlist.get(i).setQuantity(cartlist.get(i).getQuantity()-1);
                    cartDao.save(cartlist.get(i));
                }
            }
        }
    }
    public void deleteFromCart(int productId, Integer userId){
        List<Cart> cartlist = cartDao.findAll();
        for (int i = 0 ; i<cartlist.size();i++) {
            if (cartlist.get(i).getProduct_id() == productId && cartlist.get(i).getUser_id() == userId) {
                    cartDao.deleteById(cartlist.get(i).getId());
            }
        }
    }

    public List<Cart> getUsersCart(Integer userId){
        List<Cart> cartlist = cartDao.findAll();
        List<Cart> usersCart = new ArrayList<>();
        for (int i = 0 ; i<cartlist.size();i++) {
            if (cartlist.get(i).getUser_id() == userId) {
                usersCart.add(cartlist.get(i));
            }
        }
        return usersCart;
    }

    public void deleteUsersCart(Integer userId){
        List<Cart> cartlist = cartDao.findAll();
        for (int i = 0 ; i<cartlist.size();i++) {
            if (cartlist.get(i).getUser_id() == userId) {
                cartDao.deleteById(cartlist.get(i).getId());
            }
        }

    }

    public void moveCart(int userid){
        List<Cart> cartlist = cartDao.findAll();
        if (cartlist.size() == 0){
            return;
        }
        for (int i = 0 ; i<cartlist.size();i++) {
            if (cartlist.get(i).getUser_id() == 0) {
                cartlist.get(i).setUser_id(userid);
                cartDao.save(cartlist.get(i));
            }
        }
    }

}
