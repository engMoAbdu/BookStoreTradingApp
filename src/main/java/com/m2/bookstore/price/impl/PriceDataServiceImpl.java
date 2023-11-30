package com.m2.bookstore.price.impl;

import com.m2.bookstore.enums.StockNamesSupported;
import com.m2.bookstore.price.PriceDataService;
import com.m2.bookstore.price.dto.StockPriceData;

import java.util.Map;

/**
 * @author Mohammed Abdu
 * @version vr0.1
 * @createdOn Aug, 2023
 */

public class PriceDataServiceImpl implements PriceDataService {

    private static final StockPriceData APPLE_PRICE_DATA =
            new StockPriceData(StockNamesSupported.APPLE, 1000, 3000);
    private static final StockPriceData GOOGLE_PRICE_DATA =
            new StockPriceData(StockNamesSupported.GOOGLE, 100, 450);
    private static final StockPriceData MICROSOFT_PRICE_DATA =
            new StockPriceData(StockNamesSupported.MICROSOFT, 760, 1500);
    private static final Map<StockNamesSupported, StockPriceData> priceDataMap =
            Map.of(StockNamesSupported.APPLE, APPLE_PRICE_DATA,
                    StockNamesSupported.GOOGLE, GOOGLE_PRICE_DATA,
                    StockNamesSupported.MICROSOFT, MICROSOFT_PRICE_DATA);

    @Override
    public Map<StockNamesSupported, StockPriceData> getStockPriceData() {
        return priceDataMap;
    }
}
