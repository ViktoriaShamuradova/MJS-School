package com.epam.esm.service.entitydtomapper.impl;

import com.epam.esm.dto.OrderItemDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.OrderItem;
import com.epam.esm.service.entitydtomapper.DtoMapper;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper implements DtoMapper<OrderItem, OrderItemDto> {

    @Override
    public OrderItem changeToEntity(OrderItemDto orderItemDto) {
        OrderItem orderItem = new OrderItem();
        orderItem.setCount(orderItemDto.getTotalCount());
        orderItem.setCertificate(new Certificate(orderItemDto.getIdCertificate()));
        orderItem.setPriceOfCertificate(orderItemDto.getPriceOfCertificateAtTheTimeOfPurchase());

        return orderItem;
    }

    @Override
    public OrderItemDto changeToDto(OrderItem orderItem) {
        OrderItemDto orderReadItem = new OrderItemDto();

        orderReadItem.setIdCertificate(orderItem.getCertificate().getId());
        orderReadItem.setTotalCount(orderItem.getCount());
        orderReadItem.setPriceOfCertificateAtTheTimeOfPurchase(orderItem.getPriceOfCertificate());
        return orderReadItem;
    }
}
