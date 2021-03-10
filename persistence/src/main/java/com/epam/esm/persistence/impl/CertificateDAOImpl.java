package com.epam.esm.persistence.impl;

import com.epam.esm.criteria_info.CertificateCriteriaInfo;
import com.epam.esm.criteria_info.PageInfo;
import com.epam.esm.entity.Certificate;
import com.epam.esm.persistence.CertificateDAO;
import com.epam.esm.persistence.specification.Specification;
import com.epam.esm.persistence.specification_builder.impl.CertificateSpecificationBuilder;
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
public class CertificateDAOImpl extends AbstractCrudDAO<Certificate, Long, CertificateCriteriaInfo> implements CertificateDAO {

    private final CertificateSpecificationBuilder specificationBuilder;

    protected CertificateDAOImpl(EntityManager entityManager, CertificateSpecificationBuilder specificationBuilder) {
        super(entityManager);
        this.specificationBuilder = specificationBuilder;
    }

    @Override
    public long getCount() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(Certificate.class)));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public List<Certificate> findAll(PageInfo pageInfo, CertificateCriteriaInfo criteriaInfo) {
        List<Specification> specifications = specificationBuilder.build(criteriaInfo);
        return entityManager.createQuery(buildCriteriaQuery(specifications, Certificate.class))
                .setMaxResults(pageInfo.getLimit())
                .setFirstResult(pageInfo.getOffset())
                .getResultList();
    }

    @Override
    public Optional<Certificate> find(Long id) {
        Certificate certificate = entityManager.find(Certificate.class, id);
        return Optional.ofNullable(certificate);
    }
}
