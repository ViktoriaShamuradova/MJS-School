package com.epam.esm.persistence.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.persistence.CertificateDAO;
import com.epam.esm.persistence.specification.Specification;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

/**
 * class which makes queries CRUD operations on a certificate to the database
 */
@Repository
public class CertificateDAOImpl extends AbstractCrudDAO<Certificate, Long> implements CertificateDAO {

    protected CertificateDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public long getCount() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(Certificate.class)));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public List<Certificate> findAll(List<Specification> specifications, int offset, int limit) {
        return entityManager.createQuery(buildCriteriaQuery(specifications, Certificate.class))
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
    }
    @Override
    public Optional<Certificate> find(Long id) {
        Certificate certificate = entityManager.find(Certificate.class, id);
        return certificate != null ? Optional.of(certificate) : Optional.empty();
    }
}
