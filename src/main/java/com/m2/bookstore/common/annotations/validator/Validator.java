package com.m2.bookstore.common.annotations.validator;

import com.m2.bookstore.common.annotations.NotNull;
import com.m2.bookstore.common.annotations.ValidAge;
import com.m2.bookstore.common.exception.CustomDataException;
import com.m2.bookstore.common.logger.CustomLogger;

import java.lang.reflect.Field;

import static com.m2.bookstore.common.constants.Constants.UTILITY_CLASS;
import static com.m2.bookstore.common.enums.ErrorCodes.INVALID_ARGUMENTS;

/**
 * @author Mohammed Abdu
 * @version vr0.1
 * @email eng.mo.abdu@gmail.com
 * @creationDate 25 Nov 2023
 */

@SuppressWarnings("java:S3011")
public final class Validator implements CustomLogger {

    public static void validate(Object obj) {
        try {
            notNull(obj);
            validAge(obj);
            positiveNumberValidate(obj);
        } catch (IllegalAccessException e) {
            throw new CustomDataException(e.getMessage(), INVALID_ARGUMENTS.getErrorCode());
        }
    }

    private static void notNull(Object obj) throws IllegalAccessException {
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(NotNull.class)) {
                Object value = field.get(obj);
                if (value == null) {
                    throw new IllegalArgumentException("Field '" + field.getName() + "' cannot be null");
                }
            }
        }
    }

    private static void validAge(Object obj) throws IllegalAccessException {
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(ValidAge.class)) {
                Object value = field.get(obj);
                if (value instanceof Integer x) {
                    int checkedValue = x;
                    int minAge = field.getAnnotation(ValidAge.class).min();
                    int maxAge = field.getAnnotation(ValidAge.class).max();

                    if (checkedValue < minAge || checkedValue > maxAge) {
                        throw new IllegalArgumentException(field.getName() + " must be between " + minAge + " and " + maxAge + "Years");
                    }
                }
            }
        }
    }

    private static void positiveNumberValidate(Object obj) {
        if (!PositiveNumberValidator.validate(obj)) {
            throw new IllegalArgumentException();
        }
    }

    private Validator() {
        throw new IllegalStateException(UTILITY_CLASS);
    }
}
