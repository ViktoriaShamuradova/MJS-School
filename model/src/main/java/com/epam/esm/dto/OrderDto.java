package com.epam.esm.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, exclude = {"orderReadItems"})
public class OrderDto extends RepresentationModel<OrderDto> {

    private long id;
    private Set<OrderItemDto> orderReadItems;
    private BigDecimal totalSum;
    private int totalCount;
    private long userId;
    private Instant createDate;

}
