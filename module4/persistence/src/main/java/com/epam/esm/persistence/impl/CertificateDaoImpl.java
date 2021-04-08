package com.epam.esm.persistence.impl;

import com.epam.esm.criteria_info.CertificateCriteriaInfo;
import com.epam.esm.criteria_info.PageInfo;
import com.epam.esm.entity.Certificate;
import com.epam.esm.persistence.CertificateDao;
import com.epam.esm.persistence.dataspecification.CertificateSpecification;
import com.epam.esm.persistence.repository.CertificateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class CertificateDaoImpl implements CertificateDao {

    private final CertificateRepository certificateRepository;

    @Override
    public List<Certificate> findAll(CertificateCriteriaInfo criteriaInfo, PageInfo pageInfo, Sort sort) {
        CertificateSpecification specification = new CertificateSpecification(criteriaInfo);
        return certificateRepository.findAll(specification, PageRequest.of(pageInfo.getCurrentPage() - 1, pageInfo.getLimit(), sort))
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Certificate> findById(Long id) {
        return certificateRepository.findById(id);
    }

    @Override
    public Certificate save(Certificate certificate) {
        return certificateRepository.save(certificate);
    }

    @Override
    public void delete(Certificate certificate) {
        certificateRepository.delete(certificate);
    }

    @Override
    public long getCount() {
        return certificateRepository.count();
    }
}
