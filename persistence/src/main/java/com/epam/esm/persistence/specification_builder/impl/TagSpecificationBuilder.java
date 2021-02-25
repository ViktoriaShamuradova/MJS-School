package com.epam.esm.persistence.specification_builder.impl;

import com.epam.esm.criteria_info.CriteriaInfo;
import com.epam.esm.persistence.specification.Specification;
import com.epam.esm.persistence.specification.factory.FactorySpecification;
import com.epam.esm.persistence.specification_builder.SpecificationBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TagSpecificationBuilder implements SpecificationBuilder<CriteriaInfo> {

    private List<Specification> specifications;

    @Override
    public List<Specification> build(CriteriaInfo criteriaInfo) {
        specifications = new ArrayList<>();

        addSpecificationName(criteriaInfo.getName());
        return specifications;
    }

    private void addSpecificationName(String name) {
        if (name == null) return;
        specifications.add(FactorySpecification.getSpecificationByName(name));
    }
}
