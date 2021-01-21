package com.epam.esm;

import com.epam.esm.entity.Certificate;

import java.util.List;

public interface CertificateDAO {

    List<Certificate> allCertificates();

    Certificate certificateById(int id);

    int create(Certificate certificate);

    void update(int id, Certificate certificate);

    void delete(int id);

    List<Certificate> getCertificatesByTagId(int tagId);

    void addCertificateTag(int certificateId, int tagId);

    List<Certificate> getCertificatesByPartOfNameOrDescription(String name, String description);
}
