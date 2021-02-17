package com.epam.esm.service;

import java.util.List;
import java.util.Map;

public interface CrudService<T, ID> {
    List<T> findAll(Map<String, String> params);

    T create(T t);

    T find(ID id);

    void delete(ID id);

    T update(T t);
}
