package com.epam.esm.persistence.specification.factory;

import com.epam.esm.persistence.specification.SearchSpecification;
import com.epam.esm.persistence.specification.Specification;
/**
 * Class factory. Class contains methods which return Specifications. Specifications are for tag class
 */
public class FactoryTagSpecification {

    public static Specification getSpecificationByName(String name) {
        return (SearchSpecification) (criteriaBuilder, root) -> criteriaBuilder.equal(root.get(Field.NAME), name);
    }

    private static class Field {
        private final static String NAME = "name";
    }
}
