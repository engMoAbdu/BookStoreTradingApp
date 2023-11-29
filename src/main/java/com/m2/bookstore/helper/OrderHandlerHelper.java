package com.m2.bookstore.helper;

import com.m2.bookstore.common.logger.CustomLogger;
import com.m2.bookstore.enums.OrderStatus;
import com.m2.bookstore.enums.StockNamesSupported;
import com.m2.bookstore.enums.TradeEnvironments;
import com.m2.bookstore.model.Order;
import com.m2.bookstore.price.PriceDataService;
import com.m2.bookstore.utils.order.OrderBookCalculatorUtils;
import com.m2.bookstore.utils.order.OrderBookPrinterUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;

public class OrderHandlerHelper implements CustomLogger {

    private final PriceDataService priceDataService;
    TreeMap<StockNamesSupported, LinkedList<Order>> orderTreeMapHistory = new TreeMap<>();
    TreeMap<StockNamesSupported, LinkedList<Order>> liveOrdersTreeMap = new TreeMap<>();
    int volume = 0;

    public OrderHandlerHelper(PriceDataService priceDataService) {
        this.priceDataService = priceDataService;
    }

    public void addOrder(Order order) {
        orderTreeMapHistory.computeIfAbsent(StockNamesSupported.valueOf(order.getStockName().toUpperCase()), key -> new LinkedList<>()).add(order);
        liveOrdersTreeMap.computeIfAbsent(StockNamesSupported.valueOf(order.getStockName().toUpperCase()), key -> new LinkedList<>()).add(order);
        volume += order.getQuantity();
    }

    public List<Order> getOrdersByUserId(int userId) {
        List<Order> userOrders = new ArrayList<>();
        orderTreeMapHistory.forEach((stockNamesSupported, orders) ->
                orders.stream()
                        .filter(order -> order.getUserId() == userId)
                        .forEach(userOrders::add));
        return userOrders;
    }

    public void closeForTrade(int userId, String orderId) {
        liveOrdersTreeMap.forEach((key, orders) -> orders.stream()
                .filter(o -> o.getOrderId().equals(orderId) && Objects.equals(o.getUserId(), userId))
                .findFirst()
                .ifPresent(orderToClose -> {
                    StockNamesSupported stockName = StockNamesSupported.valueOf(orderToClose.getStockName());
                    LinkedList<Order> liveOrders = liveOrdersTreeMap.get(stockName);
                    liveOrders.remove(orderToClose);

                    double livePrice = priceDataService.getStockPriceData().get(stockName).getLivePrice();

                    orderToClose.setClosePrice(livePrice);
                    orderToClose.setOrderStatus(OrderStatus.CLOSED);
                    orderToClose.setPl(OrderBookCalculatorUtils.calculateProfit(orderToClose, livePrice));
                }));
    }

    public void updateLiveOrderTrades() {
        log.info("updateTrades");
        liveOrdersTreeMap.forEach((stockNamesSupported, orders) -> orders.forEach(this::checkTradeOrders));
    }

    public SortedMap<StockNamesSupported, LinkedList<Order>> getOrderTreeMapLive() {
        return liveOrdersTreeMap;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        OrderBookPrinterUtils.handleToStringResponse(TradeEnvironments.HISTORY, orderTreeMapHistory, builder);
        OrderBookPrinterUtils.handleToStringResponse(TradeEnvironments.LIVE, liveOrdersTreeMap, builder);
        return builder.toString();
    }

    private void checkTradeOrders(Order order) {
        if (order.getOrderStatus().equals(OrderStatus.OPEN)) {
            updateProfitForOpenOrder(order);
        }
        if (shouldUpdateStatusToOpen(order)) {
            updateStatusToOpenForTradesOrders(order);
        }
    }

    private void updateProfitForOpenOrder(Order order) {
        double livePrice = OrderBookCalculatorUtils.calculateLivePrice(priceDataService, order);
        order.setPl(OrderBookCalculatorUtils.calculateProfit(order, livePrice));
        OrderBookPrinterUtils.printOrderSystemTradeExecution(order);
    }

    private void updateStatusToOpenForTradesOrders(Order order) {
        order.setOrderStatus(OrderStatus.OPEN);
        order.setEntryPrice(OrderBookCalculatorUtils.calculateLivePrice(priceDataService, order));
    }

    private boolean shouldUpdateStatusToOpen(Order order) {
        double livePrice = OrderBookCalculatorUtils.calculateLivePrice(priceDataService, order);

        return switch (order.getOrderAction()) {
            case BUY -> order.getLimitPrice() <= livePrice;
            case SELL -> order.getLimitPrice() >= livePrice;
        };
    }
}
