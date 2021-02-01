package com.epam.esm.service;

import java.util.List;

public interface CrudService<T, ID> {
    List<T> findAll();

    ID create(T t);

    T find(ID id);

    void delete(ID id);

    void update(T t);


}
