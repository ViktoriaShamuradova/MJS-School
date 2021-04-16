package com.epam.esm.service.modelmapper.impl;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.OrderItemDto;
import com.epam.esm.entity.Order;
import com.epam.esm.service.modelmapper.AbstractModelMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OrderMapper extends AbstractModelMapper<OrderDto, Order, Long> {

    private final OrderItemMapper orderItemMapper;

    public OrderMapper(OrderItemMapper orderItemMapper, ModelMapper modelMapper) {
        super(modelMapper);
        this.orderItemMapper = orderItemMapper;
    }

    @Override
    public Order toEntity(OrderDto dto) {
        return Objects.isNull(dto) ? null : super.getModelMapper().map(dto, Order.class);
    }

    public OrderDto toDTO(Order entity) {
        OrderDto orderDto = Objects.isNull(entity) ? null : super.getModelMapper().map(entity, OrderDto.class);

        Set<OrderItemDto> orderItemDtos = entity.getOrderItems()
                .stream()
                .map(orderItemMapper::toDTO)
                .collect(Collectors.toSet());

        orderDto.setOrderItemDto(orderItemDtos);
        return orderDto;
    }
}
