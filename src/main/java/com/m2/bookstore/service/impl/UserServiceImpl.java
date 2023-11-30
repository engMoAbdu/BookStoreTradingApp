package com.m2.bookstore.service.impl;

import com.m2.bookstore.common.annotations.validator.Validator;
import com.m2.bookstore.common.base.service.impl.BaseDataReoServiceImpl;
import com.m2.bookstore.common.logger.CustomLogger;
import com.m2.bookstore.utils.PasswordEncoderUtils;
import com.m2.bookstore.dto.UserCreationRequest;
import com.m2.bookstore.model.User;
import com.m2.bookstore.service.UserService;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.Objects;

import static com.m2.bookstore.common.constants.Constants.M2Files.USER_FILE_NAME;
import static com.m2.bookstore.common.constants.Constants.Messages.SuccessMessages.CREATED_SUCCESSFULLY;

/**
 * @author Mohammed Abdu
 * @version vr0.1
 * @email eng.mo.abdu@gmail.com
 * @creationDate 25 Nov 2023
 */
public class UserServiceImpl extends BaseDataReoServiceImpl<Integer, User> implements UserService, CustomLogger {
    @Override
    protected String setFilename() {
        String filePathConfig = System.getProperty("file.path.config");
        return Objects.nonNull(filePathConfig) ? filePathConfig : USER_FILE_NAME;
    }

    @Override
    public String createNewUser(UserCreationRequest request) {
        int lastAddedId = this.findAll().stream().mapToInt(User::getId).max().orElse(0);
        Validator.validate(request);
        if (this.checkExistUsername(request.username())) {
            log.error("This User is already Exist, Please a try to Login");
            throw new KeyAlreadyExistsException("This User is already Exist.");
        }
        super.save(new User.UserBuilder()
                .id(++lastAddedId)
                .age(request.age())
                .fullName(request.fullName())
                .password(request.password())
                .phone(request.phone())
                .email(request.email())
                .username(request.username())
                .build());
        return CREATED_SUCCESSFULLY;
    }

    @Override
    public boolean login(String username, String password) {
        User user = findUserByUsername(username);
        return Objects.nonNull(user) && this.verifyPassword(password, user);
    }

    @Override
    public boolean checkExistUsername(String username) {
        return Objects.nonNull(findUserByUsername(username));
    }

    @Override
    public User findUserByUsername(String username) {
        return super.findAll().stream().filter(user -> user.getUsername().equals(username)).findFirst().orElse(null);
    }

    private boolean verifyPassword(String enteredPassword, User user) {
        // Hash the entered password with the extracted salt
        String hashedEnteredPassword = PasswordEncoderUtils.hashPassword(enteredPassword);

        // Compare the hashed entered password with the stored hashed password
        return user.getPassword().equals(hashedEnteredPassword);
    }
}
