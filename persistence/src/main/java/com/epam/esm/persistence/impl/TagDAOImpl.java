package com.epam.esm.persistence.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.persistence.TagDAO;
import com.epam.esm.persistence.specification.SearchSpecification;
import com.epam.esm.persistence.specification.SortSpecification;
import com.epam.esm.persistence.specification.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigInteger;
import java.util.*;

@Repository
public class TagDAOImpl implements TagDAO {
    private final static String SELECT_USED_TAG = "SELECT t.name, t.id, count(*) as count FROM mjs_school.tags t " +
            "JOIN mjs_school.certificates_tags ct ON t.id=ct.id_tag \n" +
            "JOIN mjs_school.certificates c ON c.id=ct.id_certificate \n" +
            "JOIN mjs_school.order_items oi ON oi.id_certificate=c.id \n" +
            "JOIN mjs_school.orders o ON o.id=oi.id_order \n" +
            "WHERE o.id_user=(SELECT id_user FROM mjs_school.orders GROUP BY id_user ORDER BY SUM(total_sum) DESC LIMIT 1) \n" +
            "GROUP BY t.name ORDER BY count(*)  DESC";

    private final EntityManager entityManager;

    @Autowired
    public TagDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Tag> find(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("name"), name));
        Tag tag = entityManager.createQuery(criteriaQuery).getResultStream().findFirst().orElse(null);
        return tag != null ? Optional.of(tag) : Optional.empty();
    }

    @Override
    public Map<Tag, Integer> getTagsOfUserWithHighestCostOfOrders() {
        Map<Tag, Integer> results = new HashMap<>();
        List<Object[]> resultList = entityManager.createNativeQuery(SELECT_USED_TAG).getResultList();
        for (Object[] borderTypes : resultList) {
            BigInteger id = (BigInteger) borderTypes[1];
            String name = (String) borderTypes[0];
            Tag t = new Tag(id.longValue(), name);
            BigInteger count = (BigInteger) borderTypes[2];
            results.put(t, count.intValue());
        }
        return results;
    }

    @Override
    public long getCount() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> count = criteriaBuilder.createQuery(Long.class);
        count.select(criteriaBuilder.count(count.from(Tag.class)));
        return entityManager.createQuery(count).getSingleResult();
    }

    @Override
    public Long create(Tag tag) {
        entityManager.persist(tag);
        return tag.getId();
    }

    @Override
    public List<Tag> findAll(List<Specification> specifications, int offset, int limit) {
        return entityManager.createQuery(buildCriteriaQuery(specifications)).setMaxResults(limit).setFirstResult(offset).getResultList();
    }

    @Override
    public Optional<Tag> find(Long id) {
        Tag tag = entityManager.find(Tag.class, id);
        return tag != null ? Optional.of(tag) : Optional.empty();
    }

    @Override
    public void delete(Tag tag) {
        entityManager.remove(tag);
    }

    @Override
    public void update(Tag o) {

    }

    private CriteriaQuery<Tag> buildCriteriaQuery(List<Specification> specifications) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);
        List<Predicate> predicates = new ArrayList<>();
        specifications.forEach(specification -> {
            if (specification instanceof SearchSpecification) {
                predicates.add(((SearchSpecification) specification).toPredicate(criteriaBuilder, root));
            } else {
                criteriaQuery.orderBy(((SortSpecification) specification).toOrder(criteriaBuilder, root));
            }
        });
        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        return criteriaQuery;
    }
}
