package com.epam.esm.persistence;

import com.epam.esm.entity.Certificate;


public interface CertificateDAO extends CrudDAO<Certificate, Long> {
    long getCount();
}
