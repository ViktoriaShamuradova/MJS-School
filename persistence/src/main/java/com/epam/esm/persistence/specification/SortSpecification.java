package com.epam.esm.persistence.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

public interface SortSpecification extends Specification{
    Order toOrder(CriteriaBuilder criteriaBuilder, Root root);
}
