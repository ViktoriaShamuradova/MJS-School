package com.epam.esm.service;

import com.epam.esm.dto.CartContext;
import com.epam.esm.dto.OrderReadDto;

import java.util.List;
import java.util.Map;

public interface OrderService extends CrudService<OrderReadDto, Long> {

    OrderReadDto create(CartContext cart);

    List<OrderReadDto> find(Map<String, String> params);

    List<OrderReadDto> findByUserId(long userId);
}
