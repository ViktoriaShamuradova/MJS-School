package com.epam.esm.persistence.specification.factory;

import com.epam.esm.persistence.specification.SearchSpecification;
import com.epam.esm.persistence.specification.Specification;

/**
 * Class factory. Class contains methods which return Specifications. Specifications are for Certificate class
 */
public class FactoryUserSpecification {
    public static Specification getSpecificationByName(String name) {
        return (SearchSpecification) (criteriaBuilder, root) -> criteriaBuilder.equal(root.get(Field.NAME), name);
    }

    public static Specification getSpecificationBySurname(String surname) {
        return (SearchSpecification) (criteriaBuilder, root) ->
                criteriaBuilder.equal(root.get(Field.SURNAME), surname);
    }

    private static class Field {
        private final static String NAME = "name";
        private final static String SURNAME = "surname";
    }
}
