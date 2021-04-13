package com.epam.esm.persistence;

import com.epam.esm.criteria_info.CertificateCriteriaInfo;
import com.epam.esm.entity.Certificate;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CertificateDao {
    List<Certificate> findAll(CertificateCriteriaInfo criteriaInfo, Pageable pageable);
    Optional<Certificate> findById(Long id);
    Certificate save(Certificate certificate);
    void delete(Certificate certificate);
    long getCount();
}
