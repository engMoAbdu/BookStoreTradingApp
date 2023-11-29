package com.m2.bookstore.service.impl;

import com.m2.bookstore.dto.OrderCreationRequest;
import com.m2.bookstore.enums.OrderDirection;
import com.m2.bookstore.enums.OrderType;
import com.m2.bookstore.enums.StockNamesSupported;
import com.m2.bookstore.model.Order;
import com.m2.bookstore.price.PriceDataService;
import com.m2.bookstore.price.impl.PriceDataServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static com.m2.bookstore.common.constants.Constants.JUNIT_TEST;
import static com.m2.bookstore.common.constants.Constants.Messages.SuccessMessages.CREATED_SUCCESSFULLY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatNoException;

/**
 * @author Mohammed Abdu
 * @version vr0.1
 * @email eng.mo.abdu@gmail.com
 * @creationDate 29 Nov 2023
 */

@Tag(JUNIT_TEST)
@DisplayName("Order Book Service Impl Test")
class OrderBookServiceImplTest {

    @Mock
    private PriceDataService priceDataService;

    @InjectMocks
    private OrderBookServiceImpl orderBookService;

    @BeforeEach
    void setUp() {
        priceDataService = new PriceDataServiceImpl();
        orderBookService = new OrderBookServiceImpl(priceDataService);
    }

    @Test
    void placeOrder_withValidMarketOrder_shouldCreateOrderSuccessfully() {
        OrderCreationRequest request = createMarketOrderRequest();
        String result = orderBookService.placeOrder(request);

        assertThat(result).isEqualTo(CREATED_SUCCESSFULLY);
    }

    @Test
    void placeOrder_withValidLimitOrder_shouldCreateOrderSuccessfully() {
        OrderCreationRequest request = createLimitOrderRequest();
        String result = orderBookService.placeOrder(request);

        assertThat(result).isEqualTo(CREATED_SUCCESSFULLY);
    }

    @Test
    void placeOrder_withInvalidQuantity_shouldThrowException() {
        OrderCreationRequest request = createInvalidQuantityOrderRequest();

        assertThatCode(() -> orderBookService.placeOrder(request)).isInstanceOf(ArithmeticException.class);
    }

    @Test
    void closeOrderForTrading_withValidData_shouldCloseOrderSuccessfully() {
        int userId = 1;
        String orderId = "validOrderId";

        assertThatNoException().isThrownBy(() -> orderBookService.closeOrderForTrading(userId, orderId));
    }

    @Test
    void updateLiveOrderTrades_shouldUpdateTradesSuccessfully() {
        assertThatNoException().isThrownBy(() -> orderBookService.updateLiveOrderTrades());
    }

    @Test
    void getOrdersByUserId_withValidUserId_shouldReturnOrdersForUser() {
        int userId = 1;
        List<Order> result = orderBookService.getOrdersByUserId(userId);

        assertThat(result).isNotNull();
    }

    @Test
    void orderBookToString_withValidData_shouldReturnFormattedString() {
        // Arrange
        OrderBookServiceImpl orderBookService = new OrderBookServiceImpl(priceDataService);

        OrderCreationRequest buyOrderRequest = new OrderCreationRequest(10, 100.0, "APPLE", OrderType.LIMIT, OrderDirection.BUY, 1);
        OrderCreationRequest sellOrderRequest = new OrderCreationRequest(8, 110.0, "APPLE", OrderType.LIMIT, OrderDirection.SELL, 2);

        orderBookService.placeOrder(buyOrderRequest);
        orderBookService.placeOrder(sellOrderRequest);
        String result = orderBookService.toString();

        assertThat(result).contains(OrderType.LIMIT.name()).doesNotContain(OrderType.MARKET.name());
    }

    private OrderCreationRequest createMarketOrderRequest() {
        return new OrderCreationRequest(10, 0.0, StockNamesSupported.APPLE.name(), OrderType.MARKET, OrderDirection.BUY, 1);
    }

    private OrderCreationRequest createLimitOrderRequest() {
        return new OrderCreationRequest(5, 1500.00, StockNamesSupported.GOOGLE.name(), OrderType.LIMIT, OrderDirection.SELL, 2);
    }

    private OrderCreationRequest createInvalidQuantityOrderRequest() {
        return new OrderCreationRequest(0, 0.00, StockNamesSupported.GOOGLE.name(), OrderType.MARKET, OrderDirection.BUY, 3);
    }
}
