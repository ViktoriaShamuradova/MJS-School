package com.epam.esm.persistence;

import com.epam.esm.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CertificateDAO extends JpaRepository<Certificate, Long>, JpaSpecificationExecutor<Certificate> {
}
