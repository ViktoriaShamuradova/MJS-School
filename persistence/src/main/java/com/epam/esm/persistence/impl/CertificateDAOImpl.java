package com.epam.esm.persistence.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.persistence.CertificateDAO;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * class which makes queries CRUD operations on a certificate to the database
 */

@Repository
public class CertificateDAOImpl implements CertificateDAO {

    private final EntityManager entityManager;

    @Autowired
    public CertificateDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void delete(Certificate certificate) {
        entityManager.remove(certificate);
    }

    @Override
    public long getCount() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(Certificate.class)));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }
//обязательно проверить
    @Override
    public void update(Certificate certificate) {
       Certificate certificateFromDB = entityManager.find(Certificate.class, certificate.getId());
       entityManager.getTransaction().begin();
       certificateFromDB = certificate;
       entityManager.getTransaction().commit();
    }

    @Override
    public Long create(Certificate certificate) {
        entityManager.persist(certificate);
        return certificate.getId();
    }

    @Override
    public List<Certificate> findAll(List<Specification> specifications, int offset, int limit) {
        return entityManager.createQuery(buildCriteriaQuery(specifications)).setMaxResults(limit).setFirstResult(offset).getResultList();
    }

    @Override
    public Optional<Certificate> find(Long id) {
        Certificate certificate = entityManager.find(Certificate.class, id);
        return certificate != null ? Optional.of(certificate) : Optional.empty();
    }

    private CriteriaQuery<Certificate> buildCriteriaQuery(List<Specification> specifications) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Certificate> criteriaQuery = criteriaBuilder.createQuery(Certificate.class);
        Root<Certificate> root = criteriaQuery.from(Certificate.class);
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
