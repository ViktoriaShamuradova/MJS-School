package com.epam.esm.persistence.dataspecification;

import com.epam.esm.criteria_info.OrderCriteriaInfo;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.metamodel.Order_;
import com.epam.esm.entity.metamodel.User_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderSpecification implements Specification<Order> {

    private final OrderCriteriaInfo criteriaInfo;
    private final List<Predicate> conditions = new ArrayList<>();

    public OrderSpecification(OrderCriteriaInfo criteriaInfo) {
        this.criteriaInfo = criteriaInfo;
    }

    private Specification<Order> idUserEquals(Long idUser) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal((root.join(Order_.USER).get(User_.ID)), idUser);
    }

    private Specification<Order> totalSumEquals(BigDecimal totalSum) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal((root.get(Order_.TOTAL_SUM)), totalSum);
    }

    @Override
    public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        addPredicateIdUser(root, query, criteriaBuilder);
        addPredicateTotalSum(root, query, criteriaBuilder);

        return finalPredicate(root, criteriaBuilder);
    }

    private Predicate finalPredicate(Root<Order> root, CriteriaBuilder criteriaBuilder) {
        if (conditions.isEmpty()) return null;
        Predicate finalPredicate = criteriaBuilder.and(conditions.get(0));
        for (int i = 1; i < conditions.size(); i++) {
            finalPredicate = criteriaBuilder.and(conditions.get(i));
        }
        return finalPredicate;
    }

    private void addPredicateTotalSum(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (criteriaInfo.getTotalSum() == null) return;
        conditions.add(totalSumEquals(criteriaInfo.getTotalSum()).toPredicate(root, query, criteriaBuilder));
    }

    private void addPredicateIdUser(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (criteriaInfo.getIdUser() == null) return;
        conditions.add(idUserEquals(criteriaInfo.getIdUser()).toPredicate(root, query, criteriaBuilder));
    }


}
