package com.example.demo.service;
import com.example.demo.dao.OrderDao;
import com.example.demo.entity.Cart;
import com.example.demo.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderDao orderDao;
    private final CartService cartService;
    private int orderid = 1;

    @Autowired
    public OrderService(OrderDao orderDao, CartService cartService) {
        this.orderDao = orderDao;
        this.cartService = cartService;
    }

    public void saveOrder(int userid) {
        List<Cart> newlist = cartService.getUsersCart(userid);
        Order newOrder = new Order();
        for (Cart cart : newlist) {
            newOrder.setUser_id(cart.getUser_id());
            newOrder.setOrder_id(orderid);
            newOrder.setProduct_id(cart.getProduct_id());
            newOrder.setQuantity(cart.getQuantity());
            newOrder.setPrice(cart.getPrice() * cart.getQuantity());
            orderDao.save(newOrder);
        }
        orderid += 1;
        cartService.deleteUsersCart(userid);
    }

    public List<Order> getAllOrders() {
        return orderDao.findAll();
    }

    public Order getOrderById(int orderId) {
        return orderDao.findById(orderId).get();
    }

    public void deleteOrder(int orderId) {
        orderDao.deleteById(orderId);
    }



}
