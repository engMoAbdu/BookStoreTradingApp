package com.m2.bookstore.dto;

import com.m2.bookstore.common.annotations.NotNull;
import com.m2.bookstore.common.annotations.PositiveNumber;
import com.m2.bookstore.enums.OrderDirection;
import com.m2.bookstore.enums.OrderType;
import com.m2.bookstore.utils.dialog.SystemDialogUtils;

import javax.swing.*;
import java.awt.*;

/**
 * @author Mohammed Abdu
 * @version vr0.1
 * @email eng.mo.abdu@gmail.com
 * @creationDate 26 Nov 2023
 */
public record OrderCreationRequest(
        @PositiveNumber int quantity,
        @PositiveNumber double price,
        @NotNull String stockName,
        @NotNull OrderType orderType,
        @NotNull OrderDirection orderDirection,
        @NotNull Integer userId
) {

    public static OrderCreationRequest createOrderForm(Component parentComponent, Integer userId) {
        try {
            String stockName = JOptionPane.showInputDialog(parentComponent,
                    SystemDialogUtils.headerMandatoryInputDialogMessage(
                            "Enter Stock Name:",
                            """
                                    - Apple
                                    - Microsoft
                                    - Google
                                    """
                    )
            );
            String orderType = JOptionPane.showInputDialog(parentComponent,
                    SystemDialogUtils.headerMandatoryInputDialogMessage(
                            "Enter Stock Name:",
                            """
                                    - Market
                                    - Limit
                                    """
                    )
            );

            int quantity = Integer.parseInt(JOptionPane.showInputDialog(parentComponent,
                    SystemDialogUtils.headerMandatoryWithoutOptionalInputDialogMessage("Enter Quantity:")));

            double price = (OrderType.valueOf(orderType.toUpperCase()).equals(OrderType.LIMIT)) ?
                    Double.parseDouble(JOptionPane.showInputDialog(parentComponent,
                            SystemDialogUtils.headerMandatoryWithoutOptionalInputDialogMessage("Enter price:"))) : 0.0;

            String orderAction = JOptionPane.showInputDialog(parentComponent,
                    SystemDialogUtils.headerMandatoryInputDialogMessage(
                            "Enter Order Action:",
                            """
                                    - Sell
                                    - Buy
                                    """
                    )
            );

            return new OrderCreationRequest(quantity, price, stockName.toUpperCase(), OrderType.valueOf(orderType.toUpperCase()),
                    OrderDirection.valueOf(orderAction.toUpperCase()), userId);
        } catch (Exception exception) {
            SystemDialogUtils.showErrorDialog(exception.getMessage());
        }
        return null;
    }
}
