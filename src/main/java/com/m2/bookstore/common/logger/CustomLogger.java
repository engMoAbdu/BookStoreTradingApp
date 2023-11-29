package com.m2.bookstore.common.logger;

import com.m2.bookstore.common.logger.impl.CustomLoggerImpl;

/**
 * @author Mohammed Abdu
 * @version vr0.1
 * @email eng.mo.abdu@gmail.com
 * @creationDate 26 Nov 2023
 */

public interface CustomLogger {
    CustomLoggerImpl log = new CustomLoggerImpl("CustomLogger", null);

    default void doNothing() {
        // This method does nothing and is here to satisfy SonarQube
    }
}
