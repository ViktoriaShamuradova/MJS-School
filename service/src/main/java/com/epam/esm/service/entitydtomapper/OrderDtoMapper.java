package com.epam.esm.service.entitydtomapper;

import com.epam.esm.dto.OrderReadDto;
import com.epam.esm.dto.OrderReadItemDto;
import com.epam.esm.entity.Order;

import java.util.Set;

public interface OrderDtoMapper {

    Order changeDtoToEntity(OrderReadDto orderUpdateDto);

    OrderReadDto changeEntityToDto(Order order, Set<OrderReadItemDto> orderItems);
}
