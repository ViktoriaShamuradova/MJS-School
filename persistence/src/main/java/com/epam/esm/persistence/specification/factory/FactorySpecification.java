package com.epam.esm.persistence.specification.factory;

import com.epam.esm.persistence.specification.SearchSpecification;
import com.epam.esm.persistence.specification.Specification;

public class FactorySpecification {

    public static Specification getSpecificationByName(String name) {
        return (SearchSpecification) (criteriaBuilder, root) -> criteriaBuilder.equal(root.get("name"), name);
    }

}
