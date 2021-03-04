package com.epam.esm.persistence.specification_builder.impl;

import com.epam.esm.criteria_info.CertificateCriteriaInfo;
import com.epam.esm.persistence.specification.DefaultSpecification;
import com.epam.esm.persistence.specification.SearchSpecification;
import com.epam.esm.persistence.specification.Specification;
import com.epam.esm.persistence.specification_builder.SpecificationBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Сlass forms the required list of specifications for further search for the desired certificate
 */
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
        specifications.add(DefaultSpecification.getSpecificationForEqualsByField(Field.NAME, name));
    }

    private void addSpecificationPartOfNameOrDescription(String partOfNameOrDescription) {
        if (partOfNameOrDescription == null) return;
        specifications.add(CertificateSpecification.getSpecificationByPartOfName(partOfNameOrDescription));
    }

    private void addSpecificationTagNames(List<String> tagNames) {
        if (tagNames == null) return;
        if (tagNames.isEmpty()) return;

        for (String tagName : tagNames) {
            specifications.add(DefaultSpecification
                    .getSpecificationForEqualsJoinTableField(Field.LIST_TAGS, Field.TAG_NAME, tagName));
        }
    }

    private void addSpecificationNameSortingWay(String byNameSortingWay) {
        if (byNameSortingWay == null) return;
        if (isValidSortingWay(byNameSortingWay)) {
            if (isAsc(byNameSortingWay)) {
                specifications.add(DefaultSpecification.getSpecificationForSortAsc(Field.NAME));
            } else {
                specifications.add(DefaultSpecification.getSpecificationForSortDesc(Field.NAME));
            }
        }
    }

    private void addSpecificationDateSortingWay(String byDateSortingWay) {
        if (byDateSortingWay == null) return;
        if (isValidSortingWay(byDateSortingWay)) {
            if (isAsc(byDateSortingWay)) {
                specifications.add(DefaultSpecification.getSpecificationForSortAsc(Field.CREATE_DATE));
            } else {
                specifications.add(DefaultSpecification.getSpecificationForSortDesc(Field.CREATE_DATE));
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

    private static class Field {
        private final static String CREATE_DATE = "createDate";
        private final static String UPDATE_LAST_DATE = "updateLastDate";
        private final static String NAME = "name";
        private final static String LIST_TAGS = "tags";
        private final static String DESCRIPTION = "description";
        private final static String TAG_NAME = "name";
    }

    private static class CertificateSpecification {

        public static Specification getSpecificationByPartOfName(String partOfNameOrDescription) {
            return (SearchSpecification) (criteriaBuilder, root)
                    -> criteriaBuilder.or(criteriaBuilder.like(root.get(Field.NAME), "%" + partOfNameOrDescription + "%"),
                    criteriaBuilder.like(root.get(Field.DESCRIPTION), "%" + partOfNameOrDescription + "%"));
        }

    }
}

