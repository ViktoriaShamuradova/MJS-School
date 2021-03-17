package com.epam.esm.persistence.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface SearchSpecification extends Specification {
    Predicate toPredicate(CriteriaBuilder criteriaBuilder, Root root);
}
