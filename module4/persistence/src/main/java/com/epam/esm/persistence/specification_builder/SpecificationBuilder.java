package com.epam.esm.persistence.specification_builder;

import com.epam.esm.criteria_info.CriteriaInfo;
import com.epam.esm.persistence.specification.Specification;

import java.util.List;

/**
 * SpecificationBuilder interface created for generating a list of objects Specification.
 * The list is needed to build a query in the dao layer
 *
 * @param <CRITERIA> the type of object which contains the necessary criteria  for the search
 */
public interface SpecificationBuilder<CRITERIA extends CriteriaInfo> {

    List<Specification> build(CRITERIA criteria);
}
