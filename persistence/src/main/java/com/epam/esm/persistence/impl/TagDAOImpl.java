package com.epam.esm.persistence.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.persistence.TagDAO;
import com.epam.esm.persistence.specification.Specification;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigInteger;
import java.util.*;

@Repository
public class TagDAOImpl extends AbstractCrudDAO<Tag, Long> implements TagDAO {
    private final static String SELECT_USED_TAG = "SELECT t.name, t.id, count(*) as count FROM mjs_school.orders AS o " +
            "JOIN order_items ot ON ot.id_order=o.id " +
            "JOIN certificates_tags ct ON ct.id_certificate=ot.id_certificate " +
            "JOIN tags t ON t.id=ct.id_tag " +
            "WHERE o.total_sum=(SELECT MAX(total_sum) FROM mjs_school.orders) GROUP BY t.name ORDER BY count(*) DESC ";

    protected TagDAOImpl(EntityManager entityManager) {
        super(entityManager);
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
    public List<Tag> findAll(List<Specification> specifications, int offset, int limit) {
        return entityManager.createQuery(buildCriteriaQuery(specifications, Tag.class))
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
    }

    @Override
    public Optional<Tag> find(Long id) {
        Tag tag = entityManager.find(Tag.class, id);
        return tag != null ? Optional.of(tag) : Optional.empty();
    }

    @Override
    public void update(Tag tag) {
        throw new UnsupportedOperationException();
    }

}
