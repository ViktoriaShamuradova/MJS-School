package com.epam.esm.service.modelmapper;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.OrderItemDto;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.OrderItem;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class OrderMapper {

    private final ModelMapper modelMapper;
    private final OrderItemMapper orderItemMapper;

    public Order toEntity(OrderDto dto) {
        return Objects.isNull(dto) ? null : modelMapper.map(dto, Order.class);
    }

    public OrderDto toDTO(Order entity) {
        OrderDto orderDto = Objects.isNull(entity) ? null : modelMapper.map(entity, OrderDto.class);


        Set<OrderItemDto> orderItemDtos = entity.getOrderItems()
                .stream()
                .map(orderItemMapper::toDto)
                .collect(Collectors.toSet());

        orderDto.setOrderItemDto(orderItemDtos);
        return orderDto;
    }
}
