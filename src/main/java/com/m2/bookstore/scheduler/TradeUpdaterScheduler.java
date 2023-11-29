package com.m2.bookstore.scheduler;

import com.m2.bookstore.service.OrderBookService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Mohammed Abdu
 * @version vr0.1
 * @email eng.mo.abdu@gmail.com
 * @creationDate 28 Nov 2023
 */
public class TradeUpdaterScheduler {

    private final OrderBookService orderBookService;
    private final ScheduledExecutorService scheduler;

    public TradeUpdaterScheduler(OrderBookService orderBookService) {
        this.orderBookService = orderBookService;
        this.scheduler = Executors.newScheduledThreadPool(1);
    }

    public void start() {
        scheduler.scheduleAtFixedRate(orderBookService::updateLiveOrderTrades, 0, 9000, TimeUnit.MILLISECONDS);
    }

    public void shutdown() {
        scheduler.shutdown();
    }
}
