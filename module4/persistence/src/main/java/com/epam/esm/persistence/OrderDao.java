package com.epam.esm.persistence;

import com.epam.esm.criteria_info.OrderCriteriaInfo;
import com.epam.esm.criteria_info.PageInfo;
import com.epam.esm.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderDao {
    Optional<Order> findById(Long id);
    Order save(Order order);
    long getCount();
    List<Order> findAll(OrderCriteriaInfo criteriaInfo, PageInfo pageInfo);
}
