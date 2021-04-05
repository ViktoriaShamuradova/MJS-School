package com.epam.esm.persistence.impl;

import com.epam.esm.criteria_info.PageInfo;
import com.epam.esm.criteria_info.UserCriteriaInfo;
import com.epam.esm.entity.User;
import com.epam.esm.persistence.UserDAO;
import com.epam.esm.persistence.specification.SearchSpecification;
import com.epam.esm.persistence.specification.Specification;
import com.epam.esm.persistence.specification_builder.impl.UserSpecificationBuilder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * class which makes queries CRUD operations on a user to the database
 */

@Repository
public class UserDAOImpl extends AbstractCrudDAO<User, Long, UserCriteriaInfo> implements UserDAO {

    private final UserSpecificationBuilder specificationBuilder;

    protected UserDAOImpl(EntityManager entityManager, UserSpecificationBuilder specificationBuilder) {
        super(entityManager);
        this.specificationBuilder = specificationBuilder;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        User user = null;
        Query namedQuery = entityManager.createNamedQuery("User.findByEmail");
        namedQuery.setParameter("email", email);
        try {
            user = (User) namedQuery.getSingleResult();
        } catch (NoResultException ex) {

        }
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findAll(PageInfo pageInfo, UserCriteriaInfo criteriaInfo) {
        List<Specification> specifications = specificationBuilder.build(criteriaInfo);
        return entityManager.createQuery(buildCriteriaQuery(specifications))
                .setMaxResults(pageInfo.getLimit())
                .setFirstResult(pageInfo.getOffset())
                .getResultList();
    }


    @Override
    public Optional<User> find(Long id) {
        User user = entityManager.find(User.class, id);
        return Optional.ofNullable(user);
    }

    @Override
    public long getCount() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(User.class)));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    private CriteriaQuery<User> buildCriteriaQuery(List<Specification> specifications) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        List<Predicate> predicates = new ArrayList<>();
        specifications.forEach(specification -> {
            predicates.add(((SearchSpecification) specification).toPredicate(criteriaBuilder, root));
        });
        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        return criteriaQuery;
    }

    @Override
    public void delete(User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(User user) {
        throw new UnsupportedOperationException();
    }

}
