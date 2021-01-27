package com.epam.esm.service;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;

import java.util.List;

public interface CertificateTagService {

    void add(long certificateId, long tagId);

    List<CertificateDTO> findCertificateByTagId(long id);

    List<TagDTO> findTagByCertificateId(long id);

}
