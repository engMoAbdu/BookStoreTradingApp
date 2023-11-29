package com.m2.bookstore.price;

import com.m2.bookstore.enums.StockNamesSupported;
import com.m2.bookstore.price.dto.StockPriceData;

import java.util.Map;

public interface PriceDataService {

    Map<StockNamesSupported, StockPriceData> getStockPriceData();
}
