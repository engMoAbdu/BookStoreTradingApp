package com.m2.bookstore.utils.dialog;

import com.m2.bookstore.common.exception.CustomNotFoundException;
import com.m2.bookstore.common.logger.CustomLogger;
import com.m2.bookstore.dto.OrderCreationRequest;
import com.m2.bookstore.service.OrderBookService;
import com.m2.bookstore.model.Order;
import com.m2.bookstore.model.User;
import com.m2.bookstore.service.UserService;
import com.m2.bookstore.utils.order.OrderBookPrinterUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author Mohammed Abdu
 * @version vr0.1
 * @email eng.mo.abdu@gmail.com
 * @creationDate 28 Nov 2023
 */

public final class UserDialogFormUtils implements CustomLogger {

    public static User handleLoginOrRegister(UserService userService, int option) {
        User loginedUser = null;
        switch (option) {
            case MainDialogUtils.OPTION_LOGIN:
                String username = MainDialogUtils.showUsernameInput();
                String password = MainDialogUtils.showPasswordInput();
                boolean isLogin = userService.login(username, password);
                loginedUser = validateLoginUser(isLogin, userService, username);
                if (Objects.isNull(loginedUser)) {
                    throw new CustomNotFoundException("Username or password incorrect, Kindly enter valid credentials!");
                }
                break;
            case MainDialogUtils.OPTION_REGISTER:
                return UserDialogRegistrationUtils.registerUser(userService);
            case MainDialogUtils.OPTION_CANCEL:
                log.info("User canceled the operation.");
                System.exit(0);
                break;
            default:
                log.info("WE ARE BOOK ORDER APP");
        }
        return loginedUser;
    }

    public static void handleUserAfterLoginOption(OrderBookService orderBookService, int option, User loginedUser) {
        switch (option) {
            case MainDialogUtils.OPTION_CREATE_ORDER:
                OrderCreationRequest orderForm = OrderCreationRequest.createOrderForm(null, loginedUser.getId());
                String status = orderBookService.placeOrder(orderForm);
                log.info(orderBookService.toString());
                log.info("Order " + status);
                break;
            case MainDialogUtils.OPTION_CLOSE_ORDER:
                String orderIdToClose = MainDialogUtils.showInputDialog("Enter Order ID to Close:");
                orderBookService.closeOrderForTrading(loginedUser.getId(), orderIdToClose);
                break;
            case MainDialogUtils.OPTION_CHECK_PL:
                List<Order> orders = orderBookService.getOrdersByUserId(loginedUser.getId());
                orders.forEach(
                        order -> {
                            log.info(order.getOrderId());
                            log.info(order.getPl() + "");
                        }
                );
                break;
            case MainDialogUtils.OPTION_GET_ORDER_STATUS:
                List<Order> userOrders = orderBookService.getOrdersByUserId(loginedUser.getId());
                log.info(OrderBookPrinterUtils.formatOrders(userOrders));
                break;
            case MainDialogUtils.OPTION_CANCEL_ORDER:
                log.info("User canceled the operation.");
                System.exit(0);
                break;
            default:
                log.info("Invalid option.");
        }
    }


    public static User validateLoginUser(boolean isLogin, UserService userService, String username) {
        if (isLogin) {
            log.info("Login Status: " + isLogin);
            return userService.findUserByUsername(username);
        } else {
            log.info("Username not exist Please enter valid username.");
            return null;
        }
    }

    private UserDialogFormUtils() {
    }
}

