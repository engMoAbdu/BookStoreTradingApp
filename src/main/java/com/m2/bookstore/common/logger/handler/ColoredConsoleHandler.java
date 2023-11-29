package com.m2.bookstore.common.logger.handler;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * @author Mohammed Abdu
 * @version vr0.1
 * @email eng.mo.abdu@gmail.com
 * @creationDate 26 Nov 2023
 */
public class ColoredConsoleHandler extends ConsoleHandler {

    @Override
    public synchronized void publish(LogRecord logRecord) {
        super.publish(logRecord);
        flush();
    }

    @Override
    public synchronized void setLevel(Level newLevel) throws SecurityException {
        super.setLevel(newLevel);
    }

    @Override
    public synchronized void setFormatter(java.util.logging.Formatter newFormatter) throws SecurityException {
        // Ignore the formatter set by users
        super.setFormatter(new ColoredFormatter());
    }
}