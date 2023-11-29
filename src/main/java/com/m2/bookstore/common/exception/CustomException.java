/**
 * * ********************
 * * Copyright ©2023 DubaiPolice. All rights reserved
 * * —————————————————————————————————
 * * NOTICE: All information contained herein is a property of DubaiPolice.
 * * *********************
 */

package com.m2.bookstore.common.exception;

import java.io.Serial;

/**
 * @author Mohammed Abdu
 * @version vr0.1
 * @createdOn Aug, 2023
 */

public class CustomException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 6008437565530199228L;

    private final String errorCode;

    public CustomException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public CustomException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public CustomException(Throwable cause, String errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public CustomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String errorCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
    }

    public CustomException(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
