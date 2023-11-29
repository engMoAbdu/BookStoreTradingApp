package com.m2.bookstore.utils.order;

import com.m2.bookstore.enums.StockNamesSupported;
import com.m2.bookstore.helper.OrderHandlerHelper;
import com.m2.bookstore.model.Order;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.SortedMap;

import static com.m2.bookstore.common.constants.Constants.UTILITY_CLASS;

/**
 * @author Mohammed Abdu
 * @version vr0.1
 * @email eng.mo.abdu@gmail.com
 * @creationDate 28 Nov 2023
 */
public final class OrderBookUtils {

    public static void matchOrders(String stockName, OrderHandlerHelper sellOrderSide, OrderHandlerHelper buyOrderSide) {
        getBuyOrdersBasedOnStockNameLive(stockName, buyOrderSide).ifPresent(buyOrders ->
                getSellOrdersBasedOnStockNameLive(stockName, sellOrderSide).ifPresent(sellOrders ->
                        buyOrders.stream()
                                .filter(buyOrder -> !sellOrders.isEmpty() && isMatch(buyOrder, sellOrders.get(0)))
                                .findFirst()
                                .ifPresent(buyOrder -> {
                                    Order sellOrder = sellOrders.get(0);
                                    buyOrder.setQuantity(buyOrder.getQuantity() - sellOrders.get(0).getQuantity());
                                    OrderBookPrinterUtils.printTradeExecution(buyOrder, sellOrder);
                                    sellOrders.remove(0);
                                })
                )
        );
    }

    private static Optional<List<Order>> getBuyOrdersBasedOnStockNameLive(String stockName, OrderHandlerHelper buyOrderSide) {
        return Optional.ofNullable(getAllBuyOrders(buyOrderSide).get(StockNamesSupported.valueOf(stockName.toUpperCase())));
    }

    private static Optional<List<Order>> getSellOrdersBasedOnStockNameLive(String stockName, OrderHandlerHelper sellOrderSide) {
        return Optional.ofNullable(getAllSellOrders(sellOrderSide).get(StockNamesSupported.valueOf(stockName.toUpperCase())));
    }

    private static SortedMap<StockNamesSupported, LinkedList<Order>> getAllBuyOrders(OrderHandlerHelper buyOrderSide) {
        return buyOrderSide.getOrderTreeMapLive();
    }

    private static SortedMap<StockNamesSupported, LinkedList<Order>> getAllSellOrders(OrderHandlerHelper sellOrderSide) {
        return sellOrderSide.getOrderTreeMapLive();
    }

    private static boolean isMatch(Order buyOrder, Order sellOrder) {
        return buyOrder.getLimitPrice() >= sellOrder.getLimitPrice() && Objects.equals(buyOrder.getStockName(), sellOrder.getStockName());
    }

    private OrderBookUtils() {
        throw new IllegalStateException(UTILITY_CLASS);
    }
}
