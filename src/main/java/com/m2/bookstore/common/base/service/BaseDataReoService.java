package com.m2.bookstore.common.base.service;

import com.m2.bookstore.common.base.BaseModel;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Mohammed Abdu
 * @version vr0.١
 * @email eng.mo.abdu@gmail.com
 * @creationDate 25 Nov 2023
 */
public interface BaseDataReoService<I extends Serializable, T extends BaseModel<I>> {

    void saveAll(List<T> data);

    void save(T data);

    List<T> findAll();

    void deleteAll();

    void update(List<T> newData);

    T findById(I id);

    void updateObject(I id, T updatedObject);

    <K, V extends Serializable> void saveMap(Map<K, LinkedList<V>> map);
}
