package com.m2.bookstore.common.logger.handler;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * @author Mohammed Abdu
 * @version vr0.1
 * @email eng.mo.abdu@gmail.com
 * @creationDate 26 Nov 2023
 */
public class ColoredFormatter extends Formatter {

    @Override
    public String format(LogRecord logRecord) {
        StringBuilder builder = new StringBuilder();

        // Add color based on log level
        if (logRecord.getLevel() == java.util.logging.Level.INFO) {
            builder.append("\u001B[37m");  // White color for INFO
        } else if (logRecord.getLevel() == java.util.logging.Level.WARNING) {
            builder.append("\u001B[33m");  // Yellow color for WARNING
        } else if (logRecord.getLevel() == java.util.logging.Level.SEVERE) {
            builder.append("\u001B[31m");  // Red color for SEVERE (assumed as error)
        }

        // Format log message
        builder.append(formatMessage(logRecord));

        // Reset color
        builder.append("\u001B[0m");

        return builder.toString();
    }
}