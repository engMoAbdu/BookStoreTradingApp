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
        @PositiveNumber @ValidAge(min = 7) int age,
        @NotNull String email,
        @NotNull String phone
) {

    public static UserCreationRequest createFromUserInput(Component parentComponent) {
        try {
            String fullName = JOptionPane.showInputDialog(parentComponent, "Enter Full Name:");
            String username = JOptionPane.showInputDialog(parentComponent, "Enter Username:");
            String password = JOptionPane.showInputDialog(parentComponent, "Enter Password:");
            int age = Integer.parseInt(JOptionPane.showInputDialog(parentComponent, "Enter Age:"));
            String email = JOptionPane.showInputDialog(parentComponent, "Enter Email:");
            String phone = JOptionPane.showInputDialog(parentComponent, "Enter Phone Number:");

            return new UserCreationRequest(fullName, username, password, age, email, phone);
        } catch (Exception exception) {
            SystemDialogUtils.showErrorDialog(exception.getMessage());
        }
        return null;
    }
}
