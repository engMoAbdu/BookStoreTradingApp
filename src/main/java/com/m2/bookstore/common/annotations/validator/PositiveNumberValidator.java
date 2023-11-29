package com.m2.bookstore.common.annotations.validator;

import com.m2.bookstore.common.annotations.PositiveNumber;

import java.lang.reflect.Field;

import static com.m2.bookstore.common.constants.Constants.UTILITY_CLASS;

/**
 * @author Mohammed Abdu
 * @version vr0.1
 * @email eng.mo.abdu@gmail.com
 * @creationDate 26 Nov 2023
 */

@SuppressWarnings("java:S3011")
public final class PositiveNumberValidator {

    public static <T> boolean validate(T object) {
        for (Field field : object.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(PositiveNumber.class)) {
                field.setAccessible(true);
                try {
                    Object value = field.get(object);
                    if (!isPositiveNumber(value)) {
                        return false;
                    }
                } catch (IllegalAccessException e) {
                    // Handle exception as needed
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isPositiveNumber(Object value) {
        if (value instanceof Number number) {
            return number.doubleValue() > 0;
        }
        return false;
    }

    private PositiveNumberValidator() {
        throw new IllegalStateException(UTILITY_CLASS);
    }
}