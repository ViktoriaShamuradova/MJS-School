package com.epam.esm.service.modelmapper;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.Order;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@RequiredArgsConstructor
@Component
public class OrderMapper {

    private final ModelMapper modelMapper;

    public Order toEntity(OrderDto dto) {
        return Objects.isNull(dto) ? null : modelMapper.map(dto, Order.class);
    }

    public OrderDto toDTO(Order entity) {
        return Objects.isNull(entity) ? null : modelMapper.map(entity, OrderDto.class);
    }
}
