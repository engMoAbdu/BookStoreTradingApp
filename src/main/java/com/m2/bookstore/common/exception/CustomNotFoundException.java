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

public class CustomNotFoundException extends CustomException {

    @Serial
    private static final long serialVersionUID = 7168555867098356326L;

    public CustomNotFoundException(String message) {
        super(message, "404");
    }
}
