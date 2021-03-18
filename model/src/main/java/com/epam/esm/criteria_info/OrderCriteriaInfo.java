package com.epam.esm.criteria_info;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class OrderCriteriaInfo extends CriteriaInfo {
    private Long idUser;
    private BigDecimal totalSum;
}
