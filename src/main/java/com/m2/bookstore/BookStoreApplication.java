package com.m2.bookstore;

import com.m2.bookstore.common.config.PropertiesReader;
import com.m2.bookstore.common.enums.ErrorCodes;
import com.m2.bookstore.common.exception.CustomServiceException;
import com.m2.bookstore.common.logger.CustomLogger;
import com.m2.bookstore.service.OrderBookService;
import com.m2.bookstore.service.impl.OrderBookServiceImpl;
import com.m2.bookstore.model.User;
import com.m2.bookstore.price.PriceDataService;
import com.m2.bookstore.price.impl.PriceDataServiceImpl;
import com.m2.bookstore.price.PriceGenerator;
import com.m2.bookstore.price.impl.PriceGeneratorThreadImpl;
import com.m2.bookstore.scheduler.TradeUpdaterScheduler;
import com.m2.bookstore.service.UserService;
import com.m2.bookstore.service.impl.UserServiceImpl;
import com.m2.bookstore.utils.dialog.MainDialogUtils;
import com.m2.bookstore.utils.dialog.SystemDialogUtils;
import com.m2.bookstore.utils.dialog.UserDialogFormUtils;

import java.util.Objects;

/**
 * BookStore Application Entry Point.
 * Author: Mohammed Abdu
 * Version: vr0.1
 * Email: eng.mo.abdu@gmail.com
 * Creation Date: 25 Nov 2023.
 */
public class BookStoreApplication implements CustomLogger {

    public static void main(String[] args) {
        try {
            PropertiesReader.retrieveUserFileNamesEnv(args);
            OrderBookService orderBookService = initializeTradingSystem();
            runApplication(orderBookService);
        } catch (Exception e) {
            log.error(e.getMessage());
            SystemDialogUtils.showErrorDialog(e.getMessage());
        }
    }

    static OrderBookService initializeTradingSystem() {
        try {
            // Initialize services and components
            PriceDataService priceDataService = new PriceDataServiceImpl();
            PriceGenerator priceGenerator = new PriceGeneratorThreadImpl(priceDataService);
            OrderBookService orderBookService = new OrderBookServiceImpl(priceDataService);
            TradeUpdaterScheduler tradeUpdaterScheduler = new TradeUpdaterScheduler(orderBookService);

            // Start the trade updater scheduler in a separate thread
            tradeUpdaterScheduler.start();

            // Generate prices
            priceGenerator.generate();

            return orderBookService;
        } catch (Exception e) {
            throw new CustomServiceException(e.toString(), ErrorCodes.INTERNAL_SERVER_ERROR.getErrorCode());
        }
    }

    static void runApplication(OrderBookService orderBookService) {
        UserService userService = new UserServiceImpl();
        boolean isUserLogin = false;
        int maxAttempts = 3;  // Set a maximum amount attempts to avoid infinite loop

        while (maxAttempts > 0) {
            try {
                int option = MainDialogUtils.showMainDialog(isUserLogin);

                User loggedInUser = UserDialogFormUtils.handleLoginOrRegister(userService, option);

                if (Objects.nonNull(loggedInUser)) {
                    isUserLogin = true;
                    handleUserLoggedIn(orderBookService, loggedInUser);
                    maxAttempts = 3;  // Reset the attempt counter upon successful login
                } else {
                    maxAttempts--;  // Decrement the attempt counter for unsuccessful login attempts
                    if (maxAttempts > 0) {
                        System.exit(0);
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                SystemDialogUtils.showErrorDialog(e.getMessage());
            }
        }
    }

    static void handleUserLoggedIn(OrderBookService orderBookService, User loggedInUser) {
        boolean isUserOptionSelected = true;

        while (isUserOptionSelected) {
            try {
                int userOption = MainDialogUtils.showMainDialog(true);
                UserDialogFormUtils.handleUserAfterLoginOption(orderBookService, userOption, loggedInUser);

                // Check if the user wants to go back to the main menu or exit.
                if (userOption == MainDialogUtils.OPTION_CANCEL_ORDER || userOption == MainDialogUtils.OPTION_EXIT) {
                    isUserOptionSelected = false;
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                SystemDialogUtils.showErrorDialog(e.getMessage());
            }
        }
    }
}
