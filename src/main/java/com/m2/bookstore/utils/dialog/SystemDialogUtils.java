package com.m2.bookstore.utils.dialog;

import javax.swing.*;

import static com.m2.bookstore.common.constants.Constants.Characters.STAR;
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

    public static String headerMandatoryInputDialogMessage(String title, String options) {
        return String.format(
                """
                        %s %s
                        ************************************
                        Kindly use from down options:
                        
                        %s
                        ************************************
                        """, STAR, title, options
        );
    }

    public static String headerMandatoryWithOptionalInputDialogMessage(String title, String options) {
        return String.format(
                """
                        %s
                        %s
                        """, title, options
        );
    }

    public static String headerMandatoryWithoutOptionalInputDialogMessage(String title) {
        return String.format(
                """
                        %s %s
                        """, STAR, title
        );
    }

    public static String headerTitleOnlyAndOptionalInputDialogMessage(String title) {
        return String.format(
                """
                        %s
                        """, title
        );
    }

    private SystemDialogUtils() {
        throw new IllegalStateException(UTILITY_CLASS);
    }
}
