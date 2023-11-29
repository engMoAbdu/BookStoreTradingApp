package com.m2.bookstore.utils.dialog;

import javax.swing.*;

import static com.m2.bookstore.common.constants.Constants.UTILITY_CLASS;

/**
 * @author Mohammed Abdu
 * @version vr0.1
 * @email eng.mo.abdu@gmail.com
 * @creationDate 29 Nov 2023
 */
public final class SystemDialogUtils {

    public static void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(
                null,  // Parent component (null for default)
                message,  // Message to display
                "Error",  // Dialog title
                JOptionPane.ERROR_MESSAGE  // Message type (ERROR_MESSAGE for error)
        );
    }

    private SystemDialogUtils() {
        throw new IllegalStateException(UTILITY_CLASS);
    }
}
