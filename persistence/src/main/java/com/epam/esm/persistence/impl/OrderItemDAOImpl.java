package com.epam.esm.persistence.impl;

import com.epam.esm.entity.OrderItem;
import com.epam.esm.persistence.OrderItemDAO;
import com.epam.esm.persistence.constant.OrderItemTableColumnName;
import com.epam.esm.persistence.specification.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderItemDAOImpl implements OrderItemDAO {

    @Autowired
    private final EntityManager entityManager;

    @Autowired
    public OrderItemDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<OrderItem> create(OrderItem orderItem) {
        entityManager.persist(orderItem);
        return null;
//        OrderItem orderItem = entityManager.find(OrderItem.class, id);
//        return orderItem != null ? Optional.of(orderItem) : Optional.empty();

    }


    @Override
    public Optional<OrderItem> find(Long id) {
        OrderItem orderItem = entityManager.find(OrderItem.class, id);
        return orderItem != null ? Optional.of(orderItem) : Optional.empty();
    }

    @Override
    public List<OrderItem> findByOrderId(long orderId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<OrderItem> orderItemCriteria = criteriaBuilder.createQuery(OrderItem.class);
        Root<OrderItem> orderItemRoot = orderItemCriteria.from(OrderItem.class);
        orderItemCriteria.select(orderItemRoot);
        orderItemCriteria.where(criteriaBuilder.equal(orderItemRoot.get("order").get("id"), orderId));
        return entityManager.createQuery(orderItemCriteria).getResultList();
    }

    @Override
    public void update(OrderItem o) {
    }

    @Override
    public void delete(Long aLong) {
    }

    @Override
    public List<OrderItem> findAll(List<Specification> specifications, int id, int limit) {
        return null;
    }

}
