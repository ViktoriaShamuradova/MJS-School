package com.epam.esm.persistence;

import com.epam.esm.entity.Certificate;

import java.util.List;

public interface CertificateDAO {

    List<Certificate> allCertificates();

    Certificate certificateById(long id);

    long create(Certificate certificate);

    void update(long id, Certificate certificate);

    void delete(long id);

    List<Certificate> getCertificatesByTagId(long tagId);

    void addCertificateTag(long certificateId, long tagId);

    List<Certificate> getCertificatesByPartOfNameOrDescription(String name, String description);
}
