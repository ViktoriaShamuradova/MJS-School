package com.epam.esm.persistence.specification_builder;

import com.epam.esm.criteria_info.CriteriaInfo;
import com.epam.esm.persistence.specification.Specification;

import java.util.List;

public interface SpecificationBuilder<CRITERIA extends CriteriaInfo> {
    List<Specification> build(CRITERIA criteria);
}
