package com.m2.bookstore.common.base.service.impl;

import com.m2.bookstore.common.base.BaseModel;
import com.m2.bookstore.common.base.service.BaseDataReoService;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * @author Mohammed Abdu
 * @version vr0.1
 * @email eng.mo.abdu@gmail.com
 * @creationDate 25 Nov 2023
 */
public abstract class BaseDataReoServiceImpl<I extends Serializable, T extends BaseModel<I>> implements BaseDataReoService<I, T> {

    private static final Logger LOGGER = Logger.getLogger(BaseDataReoServiceImpl.class.getName());

    protected abstract String setFilename();

    @Override
    public void saveAll(List<T> data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(setFilename(), new File(setFilename()).exists()))) {
            for (T item : data) {
                writer.write(Objects.requireNonNull(serialize(item)));
                writer.newLine();
            }
        } catch (IOException e) {
            LOGGER.info("Error ----> " + e.getMessage());
        }
    }

    @Override
    public <K, V extends Serializable> void saveMap(Map<K, LinkedList<V>> map) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(setFilename(), new File(setFilename()).exists()))) {
            for (var entry : map.entrySet()) {
                for (var value : entry.getValue()) {
                    writer.write(Objects.requireNonNull(serialize((T) value)));
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            LOGGER.info("Error saving map: " + e.getMessage());
        }
    }

    @Override
    public void save(T data) {
        this.saveAll(List.of(data));
    }

    @Override
    public List<T> findAll() {
        List<T> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(setFilename()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                T item = deserialize(line);
                data.add(item);
            }
        } catch (IOException e) {
            LOGGER.info("Error --> " + e.getMessage());
        }
        return data;
    }

    @Override
    public void deleteAll() {
        try {
            new FileWriter(setFilename(), false).close();
        } catch (IOException e) {
            LOGGER.info("Error ---> " + e.getMessage());
        }
    }

    @Override
    public void update(List<T> newData) {
        deleteAll();
        saveAll(newData);
    }

    @Override
    public T findById(I id) {
        return findAll().stream().filter(item -> getIdFromObject(item).equals(id)).findFirst()
                .orElse(null);
    }

    @Override
    public void updateObject(I id, T updatedObject) {
        List<T> data = findAll();
        for (int i = 0; i < data.size(); i++) {
            T item = data.get(i);
            if (getIdFromObject(item).equals(id)) {
                data.set(i, updatedObject);
                break;
            }
        }
        update(data);
    }

    private I getIdFromObject(T object) {
        return object.getId();
    }

    private String serialize(T object) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(object);
            byte[] bytes = bos.toByteArray();
            return Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            LOGGER.info("Error -----> " + e.getMessage());
            return null;
        }
    }

    private T deserialize(String line) {
        try {
            byte[] bytes = Base64.getDecoder().decode(line);
            try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                 ObjectInputStream ois = new ObjectInputStream(bis)) {
                return (T) ois.readObject();
            }
        } catch (IOException | ClassNotFoundException | IllegalArgumentException e) {
            LOGGER.warning("Error deserializing object: " + e.getMessage());
            return null;
        }
    }
}
