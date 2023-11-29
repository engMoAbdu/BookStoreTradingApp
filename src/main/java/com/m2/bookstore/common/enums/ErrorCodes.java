/**
 * * ********************
 * * Copyright ©2023 DubaiPolice. All rights reserved
 * * —————————————————————————————————
 * * NOTICE: All information contained herein is a property of DubaiPolice.
 * * *********************
 */

package com.m2.bookstore.common.enums;

/**
 * @author Mohammed Abdu
 * @version vr0.1
 * @createdOn Aug, 2023
 */
public enum ErrorCodes {

    // General errors
    INTERNAL_SERVER_ERROR("ERR-001", "Something went wrong"),
    INVALID_ARGUMENTS("ERR-004", "Invalid method arguments ");


    final String errorCode;
    final String message;

    ErrorCodes(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
