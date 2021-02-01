package com.epam.esm.persistence;

import java.util.List;
import java.util.Optional;

public interface CrudDAO<T, ID>{

    void update(T o);

    ID create(T o);

    List<T> findAll();

    Optional<T> find(ID id);

    void delete(ID id);
}
