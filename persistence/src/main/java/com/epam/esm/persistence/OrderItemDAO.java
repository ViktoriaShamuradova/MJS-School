package com.epam.esm.persistence;

import com.epam.esm.entity.OrderItem;

import java.util.List;

public interface OrderItemDAO extends CrudDAO<OrderItem, Long> {
    List<OrderItem> findByOrderId(long orderId);
}
