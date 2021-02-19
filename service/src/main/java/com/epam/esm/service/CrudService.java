package com.epam.esm.service;

import java.util.List;

public interface CrudService<T, ID> {
    List<T> findAll(PageInfo pageInfo);

    T create(T t);

    T find(ID id);

    void delete(ID id);

    T update(T t);
}
