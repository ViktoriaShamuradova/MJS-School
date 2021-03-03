package com.epam.esm.persistence.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Entity;
import com.epam.esm.persistence.CrudDAO;
import com.epam.esm.persistence.specification.SearchSpecification;
import com.epam.esm.persistence.specification.SortSpecification;
import com.epam.esm.persistence.specification.Specification;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractCrudDAO<T extends Entity<ID>, ID> implements CrudDAO<T, ID> {

    protected final EntityManager entityManager;

    protected AbstractCrudDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void update(T t) {
        entityManager.merge(t);
    }

    @Override
    public ID create(T t) {
        entityManager.persist(t);
        return t.getId();
    }

    @Override
    public abstract List<T> findAll(List<Specification> specifications, int offset, int limit);

    @Override
    public abstract Optional<T> find(ID id);

    @Override
    public void delete(T t) {
        entityManager.remove(t);
    }

    protected CriteriaQuery<T> buildCriteriaQuery(List<Specification> specifications, Class<T> clazz) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
        Root<T> root = criteriaQuery.from(clazz);
        List<Predicate> predicateList = new ArrayList<>();
        specifications.forEach(specification -> {
            if (specification instanceof SearchSpecification) {
                predicateList.add(((SearchSpecification) specification).toPredicate(criteriaBuilder, root));
            } else {
                criteriaQuery.orderBy(((SortSpecification) specification).toOrder(criteriaBuilder, root));
            }
        });
        criteriaQuery.where(predicateList.toArray(new Predicate[0]));
        return criteriaQuery;
    }
}
