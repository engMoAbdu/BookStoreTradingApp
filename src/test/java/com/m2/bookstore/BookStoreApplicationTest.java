package com.m2.bookstore;

import com.m2.bookstore.common.logger.CustomLogger;
import com.m2.bookstore.model.User;
import com.m2.bookstore.price.PriceDataService;
import com.m2.bookstore.price.impl.PriceDataServiceImpl;
import com.m2.bookstore.service.OrderBookService;
import com.m2.bookstore.service.UserService;
import com.m2.bookstore.service.impl.OrderBookServiceImpl;
import com.m2.bookstore.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static com.m2.bookstore.common.constants.Constants.JUNIT_TEST;
import static org.assertj.core.api.Assertions.assertThat;

@Tag(JUNIT_TEST)
class BookStoreApplicationTest implements CustomLogger {

    @Mock
    PriceDataService priceDataService;

    @Mock
    OrderBookService orderBookService;

    @Mock
    UserService userService;

    @InjectMocks
    BookStoreApplication bookStoreApplication;

    User loginUser;

    @BeforeEach
    void setUp() {
        loginUser = new User.UserBuilder()
                .id(1)
                .fullName("John Doe")
                .age(30)
                .username("john_doe")
                .phone("123-456-7890")
                .email("john.doe@example.com")
                .password("password123") // Note: Password will be hashed automatically
                .build();
        priceDataService = new PriceDataServiceImpl();
        orderBookService = new OrderBookServiceImpl(priceDataService);
        userService = new UserServiceImpl();
        bookStoreApplication = new BookStoreApplication();
    }

    @Test
    void testInitializeTradingSystem() {
        final OrderBookService result = BookStoreApplication.initializeTradingSystem();
        assertThat(result.toString()).contains("BUY SIDE");
    }
}
