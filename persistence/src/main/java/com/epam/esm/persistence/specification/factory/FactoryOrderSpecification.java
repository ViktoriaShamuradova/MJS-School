package com.epam.esm.persistence.specification.factory;

import com.epam.esm.persistence.specification.SearchSpecification;
import com.epam.esm.persistence.specification.Specification;

import java.math.BigDecimal;

/**
 * Class factory. Class contains methods which return Specifications. Specifications are for Order class
 *
 */
public class FactoryOrderSpecification {

    public static Specification getSpecificationByIdUser(Long id) {
        return (SearchSpecification) (criteriaBuilder, root) ->
                criteriaBuilder.equal(root.join(Field.USER).get("id"), id);
    }

    public static Specification getSpecificationByTotalSum(BigDecimal sum) {
        return (SearchSpecification) (criteriaBuilder, root) -> criteriaBuilder.equal(root.get(Field.TOTAL_SUM), sum);
    }

    private static class Field {
        private final static String TOTAL_SUM = "totalSum";
        private final static String USER = "user";
    }
}
