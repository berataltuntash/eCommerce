package com.example.demo.service;
import com.example.demo.dao.OrderDao;
import com.example.demo.entity.Cart;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public void saveOrder(int userId) {
        List<Cart> newlist = cartService.getUsersCart(userId);

        Order newOrder = new Order();
        newOrder.setUser(cartService.getUserById(userId)); // Assuming cartService.getUserById(userId) retrieves the user
        newOrder.setOrderId(orderid);

        int totalPrice = 0;
        for (Cart cart : newlist) {
            OrderItems orderItem = new OrderItems();
            orderItem.setUser(cartService.getUserById(userId));
            orderItem.setOrder(newOrder);
            orderItem.setProduct(cart.getProduct());
            orderItem.setQuantity(cart.getQuantity());
            newOrder.getOrderItems().add(orderItem);

            totalPrice += cart.getProduct().getPrice() * cart.getQuantity();
        }

        newOrder.setTotalPrice(totalPrice);
        newOrder.setDate(LocalDateTime.now().toString());
        orderDao.save(newOrder);

        orderid += 1;
        cartService.deleteUsersCart(userId);
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
