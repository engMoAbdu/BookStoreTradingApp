package com.m2.bookstore.utils.dialog;

import com.m2.bookstore.common.logger.CustomLogger;
import com.m2.bookstore.dto.UserCreationRequest;
import com.m2.bookstore.service.UserService;

import static com.m2.bookstore.common.constants.Constants.UTILITY_CLASS;

/**
 * @author Mohammed Abdu
 * @version vr0.1
 * @email eng.mo.abdu@gmail.com
 * @creationDate 26 Nov 2023
 */
public final class UserDialogRegistrationUtils implements CustomLogger {

    public static boolean registerUser(UserService userService) {
        boolean registrationSuccess = false;
        while (!registrationSuccess) {
            UserCreationRequest userCreationRequest = UserCreationRequest.createFromUserInput(null);
            if (userService.checkExistUsername(userCreationRequest.username())) {
                int option = MainDialogUtils.showMainDialog(false);
                switch (option) {
                    case MainDialogUtils.OPTION_LOGIN:
                        String loginUsername = MainDialogUtils.showUsernameInput();
                        String loginPassword = MainDialogUtils.showPasswordInput();
                        boolean loginStatus = userService.login(loginUsername, loginPassword);
                        log.info("Login Status: " + loginStatus);
                        registrationSuccess = true; // Exit the loop after login
                        break;
                    case MainDialogUtils.OPTION_REGISTER:
                        // Continue the loop for registration
                        break;
                    case MainDialogUtils.OPTION_CANCEL:
                        log.info("User canceled the registration.");
                        // No need to exit the loop here
                        break;
                    default:
                        log.info("WE ARE BOOK ORDER APP");
                }
            } else {
                // Username does not exist, proceed with registration
                String newUserCreationResponse = userService.createNewUser(userCreationRequest);
                log.info(newUserCreationResponse);
                registrationSuccess = true; // Exit the loop after successful registration
            }
        }
        return registrationSuccess;
    }

    private UserDialogRegistrationUtils() {
        throw new IllegalStateException(UTILITY_CLASS);
    }
}

