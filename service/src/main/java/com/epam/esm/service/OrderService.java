package com.epam.esm.service;

import com.epam.esm.criteria_info.OrderCriteriaInfo;
import com.epam.esm.dto.CartContext;
import com.epam.esm.dto.OrderDto;


public interface OrderService extends CrudService<OrderDto, Long, OrderCriteriaInfo> {
    OrderDto create(CartContext cart);
}
