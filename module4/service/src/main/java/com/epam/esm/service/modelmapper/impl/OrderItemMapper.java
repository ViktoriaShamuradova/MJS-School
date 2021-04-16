package com.epam.esm.service.modelmapper.impl;

import com.epam.esm.dto.OrderItemDto;
import com.epam.esm.entity.OrderItem;
import com.epam.esm.service.modelmapper.AbstractModelMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class OrderItemMapper extends AbstractModelMapper<OrderItemDto, OrderItem, Long> {

    public OrderItemMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    public OrderItem toEntity(OrderItemDto dto) {
        return Objects.isNull(dto) ? null : super.getModelMapper().map(dto, OrderItem.class);
    }

    @Override
    public OrderItemDto toDTO(OrderItem entity) {
        OrderItemDto orderItemDto = Objects.isNull(entity) ? null : super.getModelMapper().map(entity, OrderItemDto.class);
        orderItemDto.setIdCertificate(entity.getCertificate().getId());
        return orderItemDto;
    }
}
