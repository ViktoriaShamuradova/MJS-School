package com.epam.esm.persistence;

import com.epam.esm.persistence.specification.Specification;

import java.util.List;
import java.util.Optional;

public interface CrudDAO<T, ID> {

    void update(T o);

    ID create(T o);

    List<T> findAll(List<Specification> specifications, int offset, int limit);

    Optional<T> find(ID id);

    void delete(T t);
}
