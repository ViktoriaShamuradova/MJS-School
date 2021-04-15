package com.epam.esm.service.modelmapper;

import com.epam.esm.dto.OrderItemDto;
import com.epam.esm.entity.OrderItem;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@RequiredArgsConstructor
@Component
public class OrderItemMapper {

    private final ModelMapper modelMapper;

    public OrderItemDto toDto(OrderItem entity) {
        OrderItemDto orderItemDto = Objects.isNull(entity) ? null : modelMapper.map(entity, OrderItemDto.class);
        orderItemDto.setIdCertificate(entity.getCertificate().getId());
        return orderItemDto;
    }
}
