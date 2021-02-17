package com.epam.esm.service.entitydtomapper;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.OrderReadItemDto;
import com.epam.esm.entity.OrderItem;

public interface OrderItemMapper {

    OrderReadItemDto changeEntityToDto(OrderItem orderItem, CertificateDTO certificateDTO);

}
