package com.m2.bookstore.enums;

/**
 * @author Mohammed Abdu
 * @version vr0.1
 * @email eng.mo.abdu@gmail.com
 * @creationDate 27 Nov 2023
 */
public enum OrderStatus {

    CREATED,
    OPEN,
    CLOSED;

    // 1. We need service to start scheduler to start the function for update status
    // 2. We need another scheduler to generate prices
    // 3. We need to add two options one for close order and PL

    // Assumption
    // We not have ask bid, and we have just one price.
    // User allowed to have multi orders in the same directions or in diff directions.
    // no expirationTime for limit order

}
