package com.m2.bookstore.price.dto;

import com.m2.bookstore.enums.StockNamesSupported;

public class StockPriceData {

    private final StockNamesSupported stock;
    private final Integer minPrice;
    private final Integer maxPrice;
    private Double livePrice = 0.0;


    public StockPriceData(StockNamesSupported stock, Integer minPrice, Integer maxPrice) {
        this.stock = stock;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public StockNamesSupported getStock() {
        return stock;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public Double getLivePrice() {
        return livePrice;
    }

    public void setLivePrice(Double livePrice) {
        this.livePrice = livePrice;
    }
}
