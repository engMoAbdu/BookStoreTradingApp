package com.m2.bookstore.service;

import com.m2.bookstore.dto.OrderCreationRequest;
import com.m2.bookstore.model.Order;

import java.util.List;

/**
 * @author Mohammed Abdu
 * @version vr0.1
 * @email eng.mo.abdu@gmail.com
 * @creationDate 26 Nov 2023
 */
public interface OrderBookService {

    String placeOrder(OrderCreationRequest request);

    void closeOrderForTrading(int userId, String orderId);

    void updateLiveOrderTrades();

    List<Order> getOrdersByUserId(int userId);
}
