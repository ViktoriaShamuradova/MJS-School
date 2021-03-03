package com.epam.esm.persistence.impl;

import com.epam.esm.entity.Order;
import com.epam.esm.persistence.OrderDAO;
import com.epam.esm.persistence.specification.Specification;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderDAOImpl extends AbstractCrudDAO<Order, Long> implements OrderDAO {

    protected OrderDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public List<Order> findAll(List<Specification> specifications, int offset, int limit) {
        return entityManager.createQuery(
                buildCriteriaQuery(specifications, Order.class))
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
    }

    @Override
    public Optional<Order> find(Long id) {
        Order order = entityManager.find(Order.class, id);
        return order != null ? Optional.of(order) : Optional.empty();
    }

    @Override
    public void update(Order order) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Order order) {
        throw new UnsupportedOperationException();
    }
}
