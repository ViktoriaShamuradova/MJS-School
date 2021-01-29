package com.epam.esm.persistence;

import java.util.List;
import java.util.Optional;

public interface CrudDAO<T, ID> {
    Optional<T> find(ID id);

    void delete(ID id);

    void update(T o);

    void create(T o);

    List<T> findAll();
}
