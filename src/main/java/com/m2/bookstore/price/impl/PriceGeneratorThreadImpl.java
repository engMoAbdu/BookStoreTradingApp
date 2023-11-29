package com.m2.bookstore.price.impl;

import com.m2.bookstore.common.enums.ErrorCodes;
import com.m2.bookstore.common.exception.CustomServiceException;
import com.m2.bookstore.common.logger.CustomLogger;
import com.m2.bookstore.enums.StockNamesSupported;
import com.m2.bookstore.price.PriceDataService;
import com.m2.bookstore.price.PriceGenerator;
import com.m2.bookstore.price.dto.StockPriceData;

import java.util.Map;
import java.util.Random;
import java.util.logging.Level;

public class PriceGeneratorThreadImpl extends Thread implements PriceGenerator, CustomLogger {

    private final PriceDataService priceDataService;
    private final Random random = new Random(100);
    private static final long TIME_TO_SLEEP = 10000;

    public PriceGeneratorThreadImpl(PriceDataService priceDataService) {
        this.priceDataService = priceDataService;
    }

    @Override
    public void generate() {
        start();
    }

    @Override
    public void run() {
        while (true) {
            updatePrices();
            delay();
        }
    }

    private void delay() {
        try {
            sleep(TIME_TO_SLEEP);
        } catch (Exception e) {
            log.log(Level.WARNING, "Interrupted!", e);
            /* Clean up whatever needs to be handled before interrupting  */
            Thread.currentThread().interrupt();
            throw new CustomServiceException(e.getMessage(), ErrorCodes.INTERNAL_SERVER_ERROR.getErrorCode());
        }
    }

    private void updatePrices() {
        Map<StockNamesSupported, StockPriceData> priceData = priceDataService.getStockPriceData();
        priceData.values().forEach(
                stockPriceData -> {
                    double price = generatePrice(stockPriceData);
                    stockPriceData.setLivePrice(price);
                    String priceLogMessage = stockPriceData.getStock().name() + " : " + price;
                    log.info(priceLogMessage);
                });
    }

    private double generatePrice(StockPriceData stockPriceData) {
        return random.nextDouble(stockPriceData.getMinPrice(), stockPriceData.getMaxPrice());
    }
}
