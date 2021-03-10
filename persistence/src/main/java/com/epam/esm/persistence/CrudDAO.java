package com.epam.esm.persistence;

import com.epam.esm.criteria_info.PageInfo;

import java.util.List;
import java.util.Optional;

public interface CrudDAO<T, ID, CRITERIA> {

    void update(T o);

    ID create(T o);

    List<T> findAll(PageInfo pageInfo, CRITERIA criteria);

    Optional<T> find(ID id);

    void delete(T t);

}
