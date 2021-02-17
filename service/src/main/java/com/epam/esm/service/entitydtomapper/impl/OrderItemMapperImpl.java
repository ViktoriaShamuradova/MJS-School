package com.epam.esm.service.entitydtomapper.impl;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.OrderReadItemDto;
import com.epam.esm.entity.OrderItem;
import com.epam.esm.service.entitydtomapper.OrderItemMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OrderItemMapperImpl implements OrderItemMapper {

    @Override
    public OrderReadItemDto changeEntityToDto(OrderItem orderItem, CertificateDTO certificateDTO) {
        OrderReadItemDto orderReadItem = new OrderReadItemDto();

        orderReadItem.setCertificateDTO(certificateDTO);
        orderReadItem.setTotalCount(orderItem.getCount());
        orderReadItem.setTotalSum(orderItem.getPriceOfCertificate().multiply(BigDecimal.valueOf(orderItem.getCount())));
        orderReadItem.setPriceOfCertificateAtTheTimeOfPurchase(orderItem.getPriceOfCertificate());
        return orderReadItem;
    }
}
