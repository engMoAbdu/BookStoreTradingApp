package com.m2.bookstore.model;

import com.m2.bookstore.common.base.BaseModel;
import com.m2.bookstore.utils.PasswordEncoderUtils;

import java.io.Serializable;

/**
 * @author Mohammed Abdu
 * @version vr0.1
 * @email eng.mo.abdu@gmail.com
 * @creationDate 25 Nov 2023
 */

public class User extends BaseModel<Integer> implements Serializable {

    private final String fullName;
    private final String username;
    private final String password;
    private final String email;
    private final String phone;
    private final int age;

    private User(UserBuilder builder) {
        super.setId(builder.id);
        this.age = builder.age;
        this.username = builder.username;
        this.email = builder.email;
        this.password = builder.password;
        this.phone = builder.phone;
        this.fullName = builder.fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return String.format("""  
                {
                    "id": %d,
                    "fullName": "%s",
                    "age": %d,
                    "username": "%s"
                }""", super.getId(), fullName, age, username);
    }

    public static class UserBuilder {
        private Integer id;
        private String fullName;
        private String username;
        private String password;
        private String email;
        private String phone;
        private int age;

        public UserBuilder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public UserBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public UserBuilder age(int age) {
            this.age = age;
            return this;
        }

        public UserBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = PasswordEncoderUtils.hashPassword(password);
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
