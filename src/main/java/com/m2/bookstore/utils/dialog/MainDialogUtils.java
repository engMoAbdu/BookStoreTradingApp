package com.m2.bookstore.utils.dialog;

import javax.swing.*;

import static com.m2.bookstore.common.constants.Constants.UTILITY_CLASS;

/**
 * @author Mohammed Abdu
 * @version vr0.1
 * @email eng.mo.abdu@gmail.com
 * @creationDate 26 Nov 2023
 */
public final class MainDialogUtils {

    public static final int OPTION_LOGIN = 0;
    public static final int OPTION_REGISTER = 1;
    public static final int OPTION_CANCEL = 2;

    public static final int OPTION_CREATE_ORDER = 0;
    public static final int OPTION_CLOSE_ORDER = 1;
    public static final int OPTION_CHECK_PL = 2;
    public static final int OPTION_GET_ORDER_STATUS = 3;
    public static final int OPTION_CANCEL_ORDER = 4;

    public static final int OPTION_TRY_AGAIN = 0;
    public static final int OPTION_EXIT = 1;

    public static int showMainDialog(boolean isLoggedIn) {
        if (isLoggedIn) {
            String[] options = new String[]{"Create New Order", "Close Order", "Check Profit/Loss", "Get History", "Cancel"};
            return JOptionPane.showOptionDialog(
                    null, "Choose an option:", "Main Menu",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, options, options[options.length - 1]
            );
        } else {
            String[] options = new String[]{"Login", "Register", "Cancel"};
            int option = JOptionPane.showOptionDialog(
                    null, "Choose an option:", "Main Menu",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, options, options[options.length - 1]
            );

            if (option == JOptionPane.CLOSED_OPTION) {
                option = OPTION_CANCEL;
            }
            return option;
        }
    }


    public static String showInputDialog(String message) {
        return JOptionPane.showInputDialog(null, message);
    }

    public static int showTryAgainDialog() {
        Object[] options = {"Try Again", "Exit"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "Do you want to try again?",
                "Try Again",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        return switch (choice) {
            case JOptionPane.YES_OPTION -> OPTION_TRY_AGAIN;
            case JOptionPane.NO_OPTION -> OPTION_EXIT;
            default -> throw new IllegalStateException("Unexpected value: " + choice);
        };
    }

    public static String showUsernameInput() {
        return JOptionPane.showInputDialog(null, "Enter Username:");
    }

    public static String showPasswordInput() {
        return JOptionPane.showInputDialog(null, "Enter Password:");
    }

    public static void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void showInfoMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    private MainDialogUtils() {
        throw new IllegalStateException(UTILITY_CLASS);
    }

}
