package com.epam.esm.criteria_info;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
public class CriteriaInfo {
    @Positive
    @Min(1)
    private Long id;
}
