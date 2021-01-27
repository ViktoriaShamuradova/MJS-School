package com.epam.esm.service.impl;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.persistence.CertificateTagDAO;
import com.epam.esm.service.CertificateTagService;
import com.epam.esm.service.entitydtomapper.CertificateDtoMapper;
import com.epam.esm.service.entitydtomapper.TagDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CertificateTagServiceImpl implements CertificateTagService {

    private final CertificateTagDAO certificateTagDAO;
    private final CertificateDtoMapper certificateDtoMapper;
    private final TagDtoMapper tagDtoMapper;

    @Autowired
    CertificateTagServiceImpl(CertificateTagDAO certificateTagDAO, CertificateDtoMapper certificateDtoMapper,
                              TagDtoMapper tagDtoMapper) {
        this.certificateTagDAO = certificateTagDAO;
        this.certificateDtoMapper = certificateDtoMapper;
        this.tagDtoMapper = tagDtoMapper;
    }

    @Override
    public void add(long certificateId, long tagId) {
        certificateTagDAO.add(certificateId, tagId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CertificateDTO> findCertificateByTagId(long tagId) {
        return getListCertificateDto(certificateTagDAO.findCertificateByTagId(tagId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagDTO> findTagByCertificateId(long id) {
        List<Tag> tags = certificateTagDAO.findTagByCertificateId(id);
        return tags.stream().map(tag -> tagDtoMapper.changeTagToTagDTO(tag)).collect(Collectors.toList());
    }

    private List<CertificateDTO> getListCertificateDto(List<Certificate> certificates) {
        return certificates
                .stream()
                .map(certificate -> {
                    List<TagDTO> tags = findTagByCertificateId(certificate.getId());
                    return certificateDtoMapper.changeCertificateToDto(certificate, tags);
                })
                .collect(Collectors.toList());
    }
}
