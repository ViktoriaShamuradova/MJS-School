package com.epam.esm.persistence;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;

import java.util.List;

public interface CertificateTagDAO {

    void add(long certificateId, long tagId);

    List<Certificate> findCertificateByTagId(long id);

    List<Tag> findTagByCertificateId(long id);
}
