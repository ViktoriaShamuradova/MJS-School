package com.epam.esm.persistence;

import com.epam.esm.entity.Certificate;

import java.util.List;

public interface CertificateDAO extends CrudDAO<Certificate, Long> {

    void addLinkCertificateWithTags(long certificateId, long tagId);

    List<Certificate> findCertificateByTagId(long id);

    List<Certificate> findByPartOfNameOrDescription(String partOfNameOrDescription);

}
