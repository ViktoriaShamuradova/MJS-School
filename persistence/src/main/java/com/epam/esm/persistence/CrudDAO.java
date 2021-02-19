package com.epam.esm.persistence;

import java.util.List;
import java.util.Optional;

public interface CrudDAO<T, ID> {

    void update(T o);

    Optional<T> create(T o);

    List<T> findAll(int offset, int limit);

    Optional<T> find(ID id);

    void delete(ID id);
}
