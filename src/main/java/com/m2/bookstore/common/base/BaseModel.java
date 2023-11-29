package com.m2.bookstore.common.base;

import java.io.Serializable;

/**
 * @author Mohammed Abdu
 * @version vr0.1
 * @email eng.mo.abdu@gmail.com
 * @creationDate 25 Nov 2023
 */
public class BaseModel<I extends Serializable> implements Serializable {

    private I id;

    public BaseModel() {}

    public BaseModel(I id) {
        this.id = id;
    }


    /// Getter & Setters

    public I getId() {
        return id;
    }

    public void setId(I id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BaseModel{" +
                "id=" + id +
                '}';
    }
}
