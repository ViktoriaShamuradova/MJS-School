package com.epam.esm.persistence;

import com.epam.esm.entity.Order;

import java.util.List;

public interface OrderDAO extends CrudDAO<Order, Long> {

    List<Order> findByUserId(long userId);
}
