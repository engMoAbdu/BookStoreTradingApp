package com.m2.bookstore.utils.order;

import com.m2.bookstore.common.logger.CustomLogger;
import com.m2.bookstore.enums.OrderDirection;
import com.m2.bookstore.enums.StockNamesSupported;
import com.m2.bookstore.model.Order;
import com.m2.bookstore.price.PriceDataService;

import static com.m2.bookstore.common.constants.Constants.UTILITY_CLASS;

/**
 * @author Mohammed Abdu
 * @version vr0.1
 * @email eng.mo.abdu@gmail.com
 * @creationDate 28 Nov 2023
 */
public final class OrderBookCalculatorUtils implements CustomLogger {

    public static double calculateLivePrice(PriceDataService priceDataService, Order order) {
        return priceDataService.getStockPriceData()
                .get(StockNamesSupported.valueOf(order.getStockName()))
                .getLivePrice();
    }

    public static double calculateProfit(Order order, double livePrice) {
        if (order.getOrderAction().equals(OrderDirection.BUY)) {
            log.info("User Profit for BUY Order ID: " + order.getOrderId() + " For one Quantity is: " + calculateBuyOrderProfit(order, livePrice));
            return calculateBuyOrderProfit(order, livePrice) * order.getQuantity();
        } else {
            log.info("User Profit for SELL Order ID: " + order.getOrderId() + "For one Quantity is: " + calculateSellOrderProfit(order, livePrice));
            return calculateSellOrderProfit(order, livePrice) * order.getQuantity();
        }
    }

    public static double calculateBuyOrderProfit(Order order, double livePrice) {
        return livePrice - order.getEntryPrice();
    }

    public static double calculateSellOrderProfit(Order order, double livePrice) {
        return order.getEntryPrice() - livePrice;
    }

    private OrderBookCalculatorUtils() {
        throw new IllegalStateException(UTILITY_CLASS);
    }
}
