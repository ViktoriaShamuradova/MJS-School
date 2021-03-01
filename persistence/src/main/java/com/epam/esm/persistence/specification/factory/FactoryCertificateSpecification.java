package com.epam.esm.persistence.specification.factory;

import com.epam.esm.persistence.specification.SearchSpecification;
import com.epam.esm.persistence.specification.SortSpecification;
import com.epam.esm.persistence.specification.Specification;

/**
 * Class factory. Class contains methods which return Specifications. Specifications are for Certificate class
 *
 */
public class FactoryCertificateSpecification {

    public static final SortSpecification SORT_BY_DATE_ASC =
            (criteriaBuilder, root) -> criteriaBuilder.asc(root.get(Field.CREATE_DATE));

    public static final SortSpecification SORT_BY_DATE_DESC =
            (criteriaBuilder, root) -> criteriaBuilder.desc(root.get(Field.UPDATE_LAST_DATE));

    public static final SortSpecification SORT_BY_NAME_ASC =
            (criteriaBuilder, root) -> criteriaBuilder.asc(root.get(Field.NAME));

    public static final SortSpecification SORT_BY_NAME_DESC =
            (criteriaBuilder, root) -> criteriaBuilder.desc(root.get(Field.NAME));

    public static Specification getSpecificationByTagName(String tagName) {
        return (SearchSpecification) (criteriaBuilder, root) ->
                criteriaBuilder.equal(criteriaBuilder.upper(root.join(Field.LIST_TAGS).get(Field.NAME)), tagName.toLowerCase());
    }

    public static Specification getSpecificationByName(String name) {
        return (SearchSpecification) (criteriaBuilder, root) -> criteriaBuilder.equal(root.get(Field.NAME), name);
    }

    public static Specification getSpecificationByPartOfName(String partOfNameOrDescription) {
        return (SearchSpecification) (criteriaBuilder, root) -> criteriaBuilder.or(criteriaBuilder.like(root.get("name"), "%" + partOfNameOrDescription + "%"),
                criteriaBuilder.like(root.get(Field.DESCRIPTION), "%" + partOfNameOrDescription + "%"));
    }

    private static class Field {
        private final static String CREATE_DATE = "createDate";
        private final static String UPDATE_LAST_DATE = "updateLastDate";
        private final static String NAME = "name";
        private final static String LIST_TAGS = "tags";
        private final static String DESCRIPTION = "description";
    }
}
