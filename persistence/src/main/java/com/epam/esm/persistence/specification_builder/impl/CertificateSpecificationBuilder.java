package com.epam.esm.persistence.specification_builder.impl;

import com.epam.esm.criteria_info.CertificateCriteriaInfo;
import com.epam.esm.persistence.specification.Specification;
import com.epam.esm.persistence.specification.factory.FactoryCertificateSpecification;
import com.epam.esm.persistence.specification_builder.SpecificationBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CertificateSpecificationBuilder implements SpecificationBuilder<CertificateCriteriaInfo> {

    private List<Specification> specifications;

    @Override
    public List<Specification> build(CertificateCriteriaInfo criteriaInfo) {
        specifications = new ArrayList<>();

        addSpecificationDateSortingWay(criteriaInfo.getByDateSortingWay());
        addSpecificationNameSortingWay(criteriaInfo.getByNameSortingWay());
        addSpecificationTagNames(criteriaInfo.getTagNames());
        addSpecificationPartOfNameOrDescription(criteriaInfo.getPartOfNameOrDescription());
        addSpecificationName(criteriaInfo.getName());

        return specifications;
    }

    private void addSpecificationName(String name) {
        if (name == null) return;
        specifications.add(FactoryCertificateSpecification.getSpecificationByName(name));

    }

    private void addSpecificationPartOfNameOrDescription(String partOfNameOrDescription) {
        if (partOfNameOrDescription == null) return;
        specifications.add(FactoryCertificateSpecification.getSpecificationByPartOfName(partOfNameOrDescription));
    }

    private void addSpecificationTagNames(List<String> tagNames) {
        if (tagNames == null) return;
        if (tagNames.isEmpty()) return;

        for (String tagName : tagNames) {
            specifications.add(FactoryCertificateSpecification.getSpecificationByTagName(tagName));
        }
    }

    private void addSpecificationNameSortingWay(String byNameSortingWay) {
        if (byNameSortingWay == null) return;
        if (isValidSortingWay(byNameSortingWay)) {
            if (isAsc(byNameSortingWay)) {
                specifications.add(FactoryCertificateSpecification.SORT_BY_NAME_ASC);
            } else {
                specifications.add(FactoryCertificateSpecification.SORT_BY_NAME_DESC);
            }
        }
    }

    private void addSpecificationDateSortingWay(String byDateSortingWay) {
        if (byDateSortingWay == null) return;
        if (isValidSortingWay(byDateSortingWay)) {
            if (isAsc(byDateSortingWay)) {
                specifications.add(FactoryCertificateSpecification.SORT_BY_DATE_ASC);
            } else {
                specifications.add(FactoryCertificateSpecification.SORT_BY_DATE_DESC);
            }
        }
    }

    private boolean isValidSortingWay(String sortingWay) {
        return sortingWay.trim().equalsIgnoreCase(Constant.ASC) || sortingWay.trim().equalsIgnoreCase(Constant.DESC);
    }

    private boolean isAsc(String sortingWay) {
        return sortingWay.equalsIgnoreCase(Constant.ASC);
    }

    private static class Constant {
        private final static String ASC = "asc";
        private final static String DESC = "desc";
    }
}

