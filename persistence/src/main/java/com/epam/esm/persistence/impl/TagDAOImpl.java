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
import java.util.*;

@Repository
public class TagDAOImpl implements TagDAO {
    private final static String SELECT_USED_TAG = "select t.name, t.id, count(*) as count from mjs_school.tag t " +
            "join mjs_school.certificate_tag ct on t.id=ct.id_tag \n" +
            "join mjs_school.certificate c on c.id=ct.id_certificate \n" +
            "join mjs_school.order_item oi on oi.id_certificate=c.id \n" +
            "join mjs_school.order o on o.id=oi.id_order \n" +
            "where o.id_user=(select id_user from mjs_school.order group by id_user order by sum(total_sum) desc limit 1) \n" +
            "group by t.name order by count(*)  desc";

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

    //проверить!!!!!
    @Override
    public Map<Tag, Integer> getTagsOfUserWithHighestCostOfOrders() {
        Map<Tag, Integer> results = new HashMap<>();
        List<Object[]> resultList = entityManager.createQuery(SELECT_USED_TAG).getResultList();
        for (Object[] borderTypes : resultList) {
            results.put((Tag) borderTypes[0], (Integer) borderTypes[1]);
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
