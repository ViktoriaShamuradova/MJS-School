package com.epam.esm.persistence.specification_builder.impl;

import com.epam.esm.criteria_info.TagCriteriaInfo;
import com.epam.esm.persistence.specification.DefaultSpecification;
import com.epam.esm.persistence.specification.Specification;
import com.epam.esm.persistence.specification_builder.SpecificationBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TagSpecificationBuilder implements SpecificationBuilder<TagCriteriaInfo> {

    private List<Specification> specifications;

    @Override
    public List<Specification> build(TagCriteriaInfo criteriaInfo) {
        specifications = new ArrayList<>();

        addSpecificationName(criteriaInfo.getName());
        return specifications;
    }

    private void addSpecificationName(String name) {
        if (name == null) return;
        specifications.add(DefaultSpecification.getSpecificationForEqualsByField(Field.NAME, name));
    }

    private static class Field {
        private final static String NAME = "name";
    }
}
