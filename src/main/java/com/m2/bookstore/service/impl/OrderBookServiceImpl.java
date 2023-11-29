package com.m2.bookstore.service.impl;

import com.m2.bookstore.common.logger.CustomLogger;
import com.m2.bookstore.dto.OrderCreationRequest;
import com.m2.bookstore.enums.OrderDirection;
import com.m2.bookstore.enums.OrderStatus;
import com.m2.bookstore.enums.OrderType;
import com.m2.bookstore.service.OrderBookService;
import com.m2.bookstore.helper.OrderHandlerHelper;
import com.m2.bookstore.model.Order;
import com.m2.bookstore.price.PriceDataService;
import com.m2.bookstore.utils.order.OrderBookCalculatorUtils;
import com.m2.bookstore.utils.order.OrderBookUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static com.m2.bookstore.common.constants.Constants.Messages.SuccessMessages.CREATED_SUCCESSFULLY;

public class OrderBookServiceImpl implements OrderBookService, CustomLogger {

    private final PriceDataService priceDataService;
    OrderHandlerHelper buyOrderSide;
    OrderHandlerHelper sellOrderSide;

    public OrderBookServiceImpl(PriceDataService priceDataService) {
        this.priceDataService = priceDataService;
        this.buyOrderSide = new OrderHandlerHelper(priceDataService);
        this.sellOrderSide = new OrderHandlerHelper(priceDataService);
    }

    @Override
    public String placeOrder(OrderCreationRequest request) {
        int quantity = request.quantity();
        if (quantity <= 0) throw new ArithmeticException("Quantity of Order cannot be less than 1");

        Order order = new Order.OrderBuilder()
                .orderId(UUID.randomUUID().toString())
                .orderAction(request.orderDirection())
                .orderStatus(OrderStatus.CREATED)
                .quantity(request.quantity())
                .stockName(request.stockName())
                .orderType(request.orderType())
                .limitPrice(request.price())
                .userId(request.userId())
                .build();

        if (order.getOrderType().equals(OrderType.MARKET)) {
            this.updateStatusToOpenForTradesOrders(order);
        }

        processOrder(order);
        return CREATED_SUCCESSFULLY;
    }

    @Override
    public void closeOrderForTrading(int userId, String orderId) {
        sellOrderSide.closeForTrade(userId, orderId);
        buyOrderSide.closeForTrade(userId, orderId);
    }

    @Override
    public void updateLiveOrderTrades() {
        log.info("updateTrades");
        buyOrderSide.updateLiveOrderTrades();
        sellOrderSide.updateLiveOrderTrades();
    }

    @Override
    public List<Order> getOrdersByUserId(int userId) {
        return Stream.concat(
                        buyOrderSide.getOrdersByUserId(userId).stream(),
                        sellOrderSide.getOrdersByUserId(userId).stream())
                .toList();
    }

    private void processOrder(Order order) {
        this.saveBasedOnOrderDirection(order);
        if (order.getOrderType().equals(OrderType.LIMIT))
            OrderBookUtils.matchOrders(order.getStockName(), sellOrderSide, buyOrderSide);
    }


    private void saveBasedOnOrderDirection(Order order) {
        if (order.getOrderAction() == OrderDirection.SELL) {
            sellOrderSide.addOrder(order);
        } else {
            buyOrderSide.addOrder(order);
        }
    }

    private void updateStatusToOpenForTradesOrders(Order order) {
        order.setOrderStatus(OrderStatus.OPEN);
        order.setEntryPrice(OrderBookCalculatorUtils.calculateLivePrice(priceDataService, order));
    }

    @Override
    public String toString() {
        return String.format("""
                =====================================================
                Symbol: TEMP
                ++++++++++++++ BUY SIDE ++++++++++++++
                %s
                ++++++++++++++ SELL SIDE ++++++++++++++
                %s
                =====================================================
                """, this.buyOrderSide, this.sellOrderSide);
    }
}
