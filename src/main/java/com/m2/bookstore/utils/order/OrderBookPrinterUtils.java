package com.m2.bookstore.utils.order;

import com.m2.bookstore.common.logger.CustomLogger;
import com.m2.bookstore.enums.StockNamesSupported;
import com.m2.bookstore.enums.TradeEnvironments;
import com.m2.bookstore.model.Order;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;

import static com.m2.bookstore.common.constants.Constants.PrintFormatter.TRADE_SECTION_TYPE_HEADER;
import static com.m2.bookstore.common.constants.Constants.UTILITY_CLASS;

/**
 * @author Mohammed Abdu
 * @version vr0.1
 * @email eng.mo.abdu@gmail.com
 * @creationDate 28 Nov 2023
 */
public class OrderBookPrinterUtils implements CustomLogger {

    public static void printOrderSystemTradeExecution(Order order) {
        log.info("Trade executed: " + order.getQuantity() + " from order ID: " + order.getOrderId() +
                " with $" + order.getPl() +
                " for Stock Name: " + order.getStockName());
    }

    public static void printTradeExecution(Order buyOrder, Order sellOrder) {
        log.info("Trade executed: BUY " + buyOrder.getQuantity() + " " + buyOrder.getStockName() +
                " at $" + buyOrder.getLimitPrice() + " with SELL " + sellOrder.getQuantity() +
                " " + sellOrder.getStockName() + " at $" + sellOrder.getLimitPrice());
    }

    public static String formatOrders(List<Order> orders) {
        StringBuilder builder = new StringBuilder();
        Set<String> uniqueOrders = new HashSet<>();
        builder.append(String.format("%-15s%-15s%-15s%-15s%-15s%n", "UserID", "StockName", "Price", "Quantity", "Order Direction"));
        orders.forEach(order -> {
            if (isUniqueOrder(order, uniqueOrders)) {
                builder.append(String.format("%-15d%-15s%-15s%-15d%-15s%n",
                        order.getUserId(), order.getStockName(), order.getLimitPrice(), order.getQuantity(), order.getOrderAction()));
            }
        });
        return builder.toString();
    }

    public static void handleToStringResponse(TradeEnvironments sectionHeader, SortedMap<StockNamesSupported, LinkedList<Order>> treeMap, StringBuilder builder) {
        builder.append(String.format(TRADE_SECTION_TYPE_HEADER, sectionHeader.toString()));
        builder.append(String.format("%-15s%-15s%-15s%-15s%-15s%-15s%-15s%n", "UserID", "StockName", "Price", "Quantity", "Order Direction", "Orders", "Order ID"));
        // Set to keep track of unique combinations of price, quantity and stock.
        Set<String> uniqueOrders = new HashSet<>();
        treeMap.forEach((stockName, orderList) -> {
            int nOrdersAtPriceLevel = orderList.stream().mapToInt(Order::getQuantity).sum();
            double price = orderList.stream().mapToDouble(Order::getLimitPrice).sum();
            orderList.forEach(order -> {
                if (isUniqueOrder(order, uniqueOrders) || sectionHeader.equals(TradeEnvironments.HISTORY)) {
                    builder.append(String.format("%-15d%-15s%-15.2f%-15d%-15s%-15d%-15s%n",
                            order.getUserId(), stockName, price, nOrdersAtPriceLevel, order.getOrderType(), orderList.size(), order.getOrderId()));
                }
            });
        });
    }

    private static boolean isUniqueOrder(Order order, Set<String> uniqueOrders) {
        var orderKey = order.getLimitPrice() + "_" + order.getQuantity() + "_" + order.getStockName();
        return uniqueOrders.add(orderKey);
    }

    private OrderBookPrinterUtils() {
        throw new IllegalStateException(UTILITY_CLASS);
    }
}
