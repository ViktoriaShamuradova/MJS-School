package com.epam.esm.persistence.specification.factory;

import com.epam.esm.persistence.constant.CertificateTableColumnName;
import com.epam.esm.persistence.specification.SearchSpecification;
import com.epam.esm.persistence.specification.SortSpecification;
import com.epam.esm.persistence.specification.Specification;


public class FactoryCertificateSpecification extends FactorySpecification{

    public static final SortSpecification SORT_BY_DATE_ASC =
            (criteriaBuilder, root) -> criteriaBuilder.asc(root.get("createDate"));

    public static final SortSpecification SORT_BY_DATE_DESC =
            (criteriaBuilder, root) -> criteriaBuilder.desc(root.get("updateLastDate"));

    public static final SortSpecification SORT_BY_NAME_ASC =
            (criteriaBuilder, root) -> criteriaBuilder.asc(root.get("name"));

    public static final SortSpecification SORT_BY_NAME_DESC =
            (criteriaBuilder, root) -> criteriaBuilder.desc(root.get("name"));

    public static Specification getSpecificationByTagName(String tagName) {
        return (SearchSpecification) (criteriaBuilder, root) -> criteriaBuilder.equal(criteriaBuilder.upper(root.join("tags").get("name")), tagName.toLowerCase());
    }


    public static Specification getSpecificationByPartOfName(String partOfNameOrDescription) {
        return (SearchSpecification) (criteriaBuilder, root) -> criteriaBuilder.or(criteriaBuilder.like(root.get("name"), "%" + partOfNameOrDescription + "%"),
                criteriaBuilder.like(root.get("description"), "%" + partOfNameOrDescription + "%"));
    }
}
