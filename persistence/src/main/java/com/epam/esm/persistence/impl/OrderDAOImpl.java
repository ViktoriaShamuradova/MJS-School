package com.epam.esm.persistence.impl;

import com.epam.esm.entity.Order;
import com.epam.esm.persistence.OrderDAO;
import com.epam.esm.persistence.specification.SearchSpecification;
import com.epam.esm.persistence.specification.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderDAOImpl implements OrderDAO {

    private final EntityManager entityManager;

    @Autowired
    public OrderDAOImpl( EntityManager entityManager ) {
        this.entityManager=entityManager;
    }

    @Override
    public Long create(Order order) {
        entityManager.persist(order);
        return order.getId();
    }

    @Override
    public List<Order> findAll(List<Specification> specifications, int offset, int limit) {
        return entityManager.createQuery(buildCriteriaQuery(specifications))
        .setMaxResults(limit).setFirstResult(offset).getResultList();
    }

    @Override
    public Optional<Order> find(Long id) {
        Order order = entityManager.find(Order.class, id);
        return order != null ? Optional.of(order) : Optional.empty();
    }

    private CriteriaQuery<Order> buildCriteriaQuery(List<Specification> specifications) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        List<Predicate> predicateList = new ArrayList<>();
        specifications.forEach(specification -> predicateList.add(((SearchSpecification)specification).toPredicate(criteriaBuilder, root)));
        criteriaQuery.where(predicateList.toArray(new Predicate[0]));
        return criteriaQuery;
    }

    @Override
    public void update(Order o) {

    }

    @Override
    public void delete(Order order) {
    }
}
