package com.epam.esm.service;

import com.epam.esm.criteria_info.CriteriaInfo;
import com.epam.esm.criteria_info.PageInfo;

import java.util.List;

public interface CrudService<T, ID, CRITERIA extends CriteriaInfo> {

    T create(T t);

    T findById(ID id);

    boolean delete(ID id);

    T update(T t);

    List<T> find(PageInfo pageInfo, CRITERIA criteriaInfo);
}
