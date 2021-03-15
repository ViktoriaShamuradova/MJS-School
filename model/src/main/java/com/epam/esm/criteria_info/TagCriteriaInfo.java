package com.epam.esm.criteria_info;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class TagCriteriaInfo extends CriteriaInfo {
    private String name;
}
