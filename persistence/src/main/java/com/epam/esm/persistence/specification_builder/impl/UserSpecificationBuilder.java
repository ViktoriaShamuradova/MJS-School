package com.epam.esm.persistence.specification_builder.impl;

import com.epam.esm.criteria_info.UserCriteriaInfo;
import com.epam.esm.persistence.specification.DefaultSpecification;
import com.epam.esm.persistence.specification.Specification;
import com.epam.esm.persistence.specification_builder.SpecificationBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Сlass forms the required list of specifications for further search for the desired user
 */
@Component
public class UserSpecificationBuilder implements SpecificationBuilder<UserCriteriaInfo> {
    private List<Specification> specifications;

    @Override
    public List<Specification> build(UserCriteriaInfo userCriteriaInfo) {
        specifications = new ArrayList<>();

        addSpecificationName(userCriteriaInfo.getName());
        addSpecificationSurname(userCriteriaInfo.getSurname());

        return specifications;
    }

    private void addSpecificationName(String name) {
        if (name == null) return;
        specifications.add(DefaultSpecification.getSpecificationForEqualsByField(Field.NAME, name));
    }

    private void addSpecificationSurname(String surname) {
        if (surname == null) return;
        specifications.add(DefaultSpecification.getSpecificationForEqualsByField(Field.SURNAME, surname));
    }

    private static class Field {
        private final static String NAME = "name";
        private final static String SURNAME = "surname";
    }
}
