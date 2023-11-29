package com.m2.bookstore.service;

import com.m2.bookstore.common.base.service.BaseDataReoService;
import com.m2.bookstore.dto.UserCreationRequest;
import com.m2.bookstore.model.User;

/**
 * @author Mohammed Abdu
 * @version vr0.1
 * @email eng.mo.abdu@gmail.com
 * @creationDate 25 Nov 2023
 */
public interface UserService extends BaseDataReoService<Integer, User> {

    String createNewUser(UserCreationRequest request);

    boolean login(String username, String password);

    boolean checkExistUsername(String username);

    User findUserByUsername(String username);
}
