package com.m2.bookstore.common.logger.impl;

import com.m2.bookstore.common.logger.handler.ColoredConsoleHandler;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static com.m2.bookstore.common.constants.Constants.Characters.NEWLINE;

/**
 * @author Mohammed Abdu
 * @version vr0.1
 * @email eng.mo.abdu@gmail.com
 * @creationDate 26 Nov 2023
 */
public class CustomLoggerImpl extends Logger {

    public CustomLoggerImpl(String name, String resourceBundleName) {
        super(name, resourceBundleName);
        setLevel(Level.ALL);  // Set the desired log level
        addHandler(new ColoredConsoleHandler());  // Example: Add a console handler
    }

    public void error(String message) {
        log(new LogRecord(Level.SEVERE, message));
    }

    @Override
    public void log(LogRecord logRecord) {
        logRecord.setMessage(logRecord.getMessage() + NEWLINE);
        super.log(logRecord);
    }
}
