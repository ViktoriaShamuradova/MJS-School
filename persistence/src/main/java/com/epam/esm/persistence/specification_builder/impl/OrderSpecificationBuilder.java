package com.epam.esm.persistence.specification_builder.impl;

import com.epam.esm.criteria_info.OrderCriteriaInfo;
import com.epam.esm.persistence.specification.Specification;
import com.epam.esm.persistence.specification.factory.FactoryOrderSpecification;
import com.epam.esm.persistence.specification_builder.SpecificationBuilder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
/**
 * Сlass forms the required list of specifications for further search for the desired order
 */
@Component
public class OrderSpecificationBuilder implements SpecificationBuilder<OrderCriteriaInfo> {
    private List<Specification> specifications;

    @Override
    public List<Specification> build(OrderCriteriaInfo orderCriteriaInfo) {
        specifications = new ArrayList<>();

        addSpecificationIdUser(orderCriteriaInfo.getIdUser());
        addSpecificationTotalSum(orderCriteriaInfo.getTotalSum());
        return specifications;
    }

    private void addSpecificationTotalSum(BigDecimal totalSum) {
        if (totalSum == null) return;
        specifications.add(FactoryOrderSpecification.getSpecificationByTotalSum(totalSum));
    }

    private void addSpecificationIdUser(Long idUser) {
        if (idUser == null) return;
        specifications.add(FactoryOrderSpecification.getSpecificationByIdUser(idUser));
    }
}

