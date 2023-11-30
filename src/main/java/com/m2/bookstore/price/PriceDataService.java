package com.m2.bookstore.price;

import com.m2.bookstore.enums.StockNamesSupported;
import com.m2.bookstore.price.dto.StockPriceData;

import java.util.Map;

/**
 * @author Mohammed Abdu
 * @version vr0.1
 * @createdOn Aug, 2023
 */
public interface PriceDataService {

    Map<StockNamesSupported, StockPriceData> getStockPriceData();
}
