package com.epam.esm.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"orderReadItems"})
public class OrderDto extends RepresentationModel<OrderDto> {

    @Positive
    @Min(1)
    private long id;
    private Set<OrderItemDto> orderReadItems;
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=5, fraction=2)
    private BigDecimal totalSum;
    @Min(1)
    @Max(100)
    private int totalCount;
    @Positive
    @Min(1)
    private long userId;
    @Null(message = "order create date cannot be defined by users")
    private Instant createDate;
}
