package com.epam.esm.service.entitydtomapper.impl;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.OrderItemDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.OrderItem;
import com.epam.esm.entity.User;
import com.epam.esm.service.entitydtomapper.DtoMapper;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OrderDtoMapper implements DtoMapper<Order, OrderDto> {

    @Override
    public Order changeToEntity(OrderDto orderDto) {
        Order order = new Order();
        order.setUser(new User(orderDto.getUserId()));
        order.setTotalSum(orderDto.getTotalSum());
        order.setCount(orderDto.getTotalCount());
        order.setCreateDate(orderDto.getCreateDate());

        Set<OrderItemDto> orderItemDtos = orderDto.getOrderReadItems();
        Set<OrderItem> orderItems = orderItemDtos.stream()
                .map(orderItemDto ->
                        new OrderItem(new Certificate(orderItemDto.getIdCertificate()),
                                orderItemDto.getTotalCount(), new Order(),
                                orderItemDto.getPriceOfCertificateAtTheTimeOfPurchase()))
                .collect(Collectors.toSet());
        order.setOrderItems(orderItems);

        return order;
    }

    @Override
    public OrderDto changeToDto(Order order) {
        OrderDto orderReadDto = new OrderDto();
        orderReadDto.setId(order.getId());
        orderReadDto.setCreateDate(order.getCreateDate());
        orderReadDto.setTotalCount(order.getCount());
        orderReadDto.setTotalSum(order.getTotalSum());
        orderReadDto.setUserId(order.getUser().getId());

        Set<OrderItem> orderItems = order.getOrderItems();
        Set<OrderItemDto> orderItemDtos = orderItems.stream()
                .map(orderItem -> new OrderItemDto(orderItem.getCertificate().getId(),
                        orderItem.getCount(), orderItem.getPriceOfCertificate()))
                .collect(Collectors.toSet());
        orderReadDto.setOrderReadItems(orderItemDtos);
        return orderReadDto;
    }

}
