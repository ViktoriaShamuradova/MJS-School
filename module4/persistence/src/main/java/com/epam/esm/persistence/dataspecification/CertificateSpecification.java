package com.epam.esm.persistence.dataspecification;

import com.epam.esm.criteria_info.CertificateCriteriaInfo;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.metamodel.Certificate_;
import com.epam.esm.entity.metamodel.Tag_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class CertificateSpecification implements Specification<Certificate> {

    private final CertificateCriteriaInfo criteriaInfo;
    private final List<Predicate> conditions = new ArrayList<>();

    public CertificateSpecification(CertificateCriteriaInfo criteriaInfo) {
        this.criteriaInfo = criteriaInfo;
    }

    private Specification<Certificate> nameEquals(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Certificate_.NAME), name);
    }

    private Specification<Certificate> nameOrDescriptionLike(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(criteriaBuilder.like(root.get(Certificate_.NAME), "%" + name + "%"),
                criteriaBuilder.like(root.get(Certificate_.DESCRIPTION), "%" + name + "%"));
    }

    private Specification<Certificate> tagNamesEqual(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal((root.join(Certificate_.TAGS).get(Tag_.NAME)), name.trim());
    }


    @Override
    public Predicate toPredicate(Root<Certificate> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        addPredicateName(root, query, criteriaBuilder);
        addPredicateNameOrDescription(root, query, criteriaBuilder);
        addPredicateTagNames(root, query, criteriaBuilder);

        return finalPredicate(root, criteriaBuilder);
    }

    private Predicate finalPredicate(Root<Certificate> root, CriteriaBuilder criteriaBuilder) {
        if (conditions.size() == 1) {
            return conditions.get(0);
        }
        if (conditions.isEmpty()) return null;

        Predicate finalPredicate = criteriaBuilder.and(conditions.get(0));
        for (int i = 1; i < conditions.size(); i++) {
            finalPredicate = criteriaBuilder.and(conditions.get(i));
        }
        return finalPredicate;
    }

    private void addPredicateTagNames(Root<Certificate> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (criteriaInfo.getTagNames() == null || criteriaInfo.getTagNames().isEmpty()) return;
        List<String> tagNames = criteriaInfo.getTagNames();
        for (String tagName : tagNames) {
            conditions.add(tagNamesEqual(tagName).toPredicate(root, query, criteriaBuilder));
        }
    }

    private void addPredicateNameOrDescription(Root<Certificate> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (criteriaInfo.getPartOfNameOrDescription() == null) return;
        conditions.add(nameOrDescriptionLike(criteriaInfo.getPartOfNameOrDescription()).toPredicate(root, query, criteriaBuilder));
    }

    private void addPredicateName(Root<Certificate> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (criteriaInfo.getName() == null) return;
        conditions.add(nameEquals(criteriaInfo.getName()).toPredicate(root, query, criteriaBuilder));
    }
}
