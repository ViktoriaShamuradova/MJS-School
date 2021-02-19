package com.epam.esm.service.entitydtomapper.impl;

import com.epam.esm.dto.OrderReadDto;
import com.epam.esm.dto.OrderReadItemDto;
import com.epam.esm.entity.Order;
import com.epam.esm.service.entitydtomapper.OrderDtoMapper;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class OrderDtoMapperImpl implements OrderDtoMapper {
    @Override
    public Order changeDtoToEntity(OrderReadDto orderUpdateDto) {
        Order order = new Order();
       // order.setUserId(orderUpdateDto.getUserId());
        order.setTotalSum(orderUpdateDto.getTotalSum());
        order.setCount(orderUpdateDto.getTotalCount());

        return order;
    }

    @Override
    public OrderReadDto changeEntityToDto(Order order, Set<OrderReadItemDto> orderItems) {
        OrderReadDto orderReadDto = new OrderReadDto();
        orderReadDto.setId(order.getId());
        orderReadDto .setCreateDate(order.getCreateDate());
        orderReadDto.setTotalCount(order.getCount());
       // orderReadDto.setUserId(order.getUserId());
        orderReadDto.setTotalSum(order.getTotalSum());
        orderReadDto.setOrderReadItems(orderItems);
        return orderReadDto;
    }
}
