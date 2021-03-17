package com.epam.esm.criteria_info;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Class containing information about certificate from url to build desired query
 */

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class CertificateCriteriaInfo extends CriteriaInfo {
    private String byDateSortingWay;
    private String byNameSortingWay;
    private List<String> tagNames;
    private String partOfNameOrDescription;
    private String name;
}
