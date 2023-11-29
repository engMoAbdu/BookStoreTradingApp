package com.m2.bookstore.model;

import com.m2.bookstore.common.base.BaseModel;
import com.m2.bookstore.enums.OrderDirection;
import com.m2.bookstore.enums.OrderStatus;
import com.m2.bookstore.enums.OrderType;

import java.io.Serializable;

/**
 * @author Mohammed Abdu
 * @version vr0.1
 * @email eng.mo.abdu@gmail.com
 * @creationDate 25 Nov 2023
 */
public class Order extends BaseModel<Integer> implements Serializable {

    private final String orderId;
    private int quantity;
    private double limitPrice;
    private final String stockName;
    private final OrderType orderType;
    private final OrderDirection orderDirection;
    private OrderStatus orderStatus;
    private final Integer userId;
    private double entryPrice;
    private double closePrice;
    private double pl;

    public Order(OrderBuilder orderBuilder) {
        this.orderStatus = orderBuilder.orderStatus;
        this.quantity = orderBuilder.quantity;
        this.limitPrice = orderBuilder.limitPrice;
        this.orderId = orderBuilder.orderId;
        this.orderDirection = orderBuilder.orderDirection;
        this.stockName = orderBuilder.stockName;
        this.orderType = orderBuilder.orderType;
        this.userId = orderBuilder.userId;

        orderBuilder.adjustPriceBasedOnOrderType();

    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getLimitPrice() {
        return limitPrice;
    }

    public String getOrderId() {
        return orderId;
    }

    public double getEntryPrice() {
        return entryPrice;
    }

    public void setEntryPrice(double entryPrice) {
        this.entryPrice = entryPrice;
    }

    public double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(double closePrice) {
        this.closePrice = closePrice;
    }

    public double getPl() {
        return pl;
    }

    public void setPl(double pl) {
        this.pl = pl;
    }

    public OrderDirection getOrderAction() {
        return orderDirection;
    }

    public String getStockName() {
        return stockName;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void updateQuantity(int newQuantity) {
        if (newQuantity < 1) {
            throw new ArithmeticException("Order quantity cannot be less than 1");
        }
        this.quantity = newQuantity;
    }

    public void updatePrice(double newPrice) {
        if (newPrice < 0) {
            throw new ArithmeticException("Order price cannot be less than 1");
        }
        this.limitPrice = newPrice;
    }

    @Override
    public String toString() {
        return String.format("""  
                {
                    "id": %d,
                    "OrderId": "%s",
                    "quantity": %d,
                    "side": "%s"
                }""", super.getId(), orderId, quantity, orderDirection);
    }

    public static class OrderBuilder {
        private String orderId;
        private int quantity;
        private double limitPrice;
        private String stockName;
        private OrderType orderType;
        private OrderDirection orderDirection;
        private OrderStatus orderStatus;
        private Integer userId;

        public OrderBuilder orderId(String orderId) {
            this.orderId = orderId;
            return this;
        }

        public OrderBuilder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public OrderBuilder limitPrice(double price) {
            this.limitPrice = price;
            return this;
        }

        public OrderBuilder userId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public OrderBuilder stockName(String stockName) {
            this.stockName = stockName;
            return this;
        }

        public OrderBuilder orderType(OrderType orderType) {
            this.orderType = orderType;
            return this;
        }

        public OrderBuilder orderAction(OrderDirection orderDirection) {
            this.orderDirection = orderDirection;
            return this;
        }

        public OrderBuilder orderStatus(OrderStatus orderStatus) {
            this.orderStatus = orderStatus;
            return this;
        }

        public Order build() {
            return new Order(this);
        }

        private void adjustPriceBasedOnOrderType() {
            if (orderType == OrderType.MARKET) {
                if (orderDirection == OrderDirection.SELL) {
                    this.limitPrice = Double.MAX_VALUE;
                } else {
                    this.limitPrice = Double.MIN_VALUE;
                }
            }
        }
    }
}
