package com.m2.bookstore.dto;

import com.m2.bookstore.common.annotations.NotNull;
import com.m2.bookstore.common.annotations.PositiveNumber;
import com.m2.bookstore.common.annotations.ValidAge;
import com.m2.bookstore.utils.dialog.SystemDialogUtils;

import javax.swing.*;
import java.awt.*;

/**
 * @author Mohammed Abdu
 * @version vr0.1
 * @email eng.mo.abdu@gmail.com
 * @creationDate 25 Nov 2023
 */
public record UserCreationRequest(
        @NotNull String fullName,
        @NotNull String username,
        @NotNull String password,
        @PositiveNumber @ValidAge int age,
        @NotNull String email,
        @NotNull String phone
) {

    public static UserCreationRequest createFromUserInput(Component parentComponent) {
        try {
            String fullName = JOptionPane.showInputDialog(parentComponent,
                    SystemDialogUtils.headerMandatoryWithoutOptionalInputDialogMessage("Enter Full Name:"));

            String username = JOptionPane.showInputDialog(parentComponent,
                    SystemDialogUtils.headerMandatoryWithoutOptionalInputDialogMessage("Enter Username:"));

            String password = JOptionPane.showInputDialog(parentComponent,
                    SystemDialogUtils.headerMandatoryWithoutOptionalInputDialogMessage("Enter Password:"));

            int age = Integer.parseInt(JOptionPane.showInputDialog(parentComponent,
                            SystemDialogUtils.headerMandatoryInputDialogMessage(
                                    "Enter Age:",
                                    "Age must be more than 7 Years"
                            )
                    )
            );

            String email = JOptionPane.showInputDialog(parentComponent,
                    SystemDialogUtils.headerMandatoryWithoutOptionalInputDialogMessage("Enter Email:"));

            String phone = JOptionPane.showInputDialog(parentComponent,
                    SystemDialogUtils.headerMandatoryWithoutOptionalInputDialogMessage("Enter Phone Number:"));

            return new UserCreationRequest(fullName, username, password, age, email, phone);
        } catch (Exception exception) {
            SystemDialogUtils.showErrorDialog(exception.getMessage());
        }
        return null;
    }
}
