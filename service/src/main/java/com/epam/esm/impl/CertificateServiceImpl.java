package com.epam.esm.impl;

import com.epam.esm.CertificateDAO;
import com.epam.esm.CertificateService;
import com.epam.esm.TagService;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entitydtomapper.CertificateDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CertificateServiceImpl implements CertificateService {


    private CertificateDAO certificateDAO;
    @Autowired
    private TagService tagService;

    private CertificateDtoMapper certificateDtoMapper;

    @Autowired
    public CertificateServiceImpl(CertificateDAO certificateDAO, CertificateDtoMapper certificateDtoMapper) {
        this.certificateDAO = certificateDAO;
        this.certificateDtoMapper = certificateDtoMapper;

    }


    @Override
    public List<CertificateDTO> getAllCertificates() {

        List<Certificate> certificates = certificateDAO.allCertificates();
        return certificates
                .stream()
                .map(certificate -> {
                    List<TagDTO> tags = tagService.getTagsByCertificateId(certificate.getId());
                    return certificateDtoMapper.changeCertificateToDto(certificate, tags);
                })
                .collect(Collectors.toList());

    }

    @Override
    public void saveCertificate(CertificateDTO certificateDTO) {
        LocalDateTime timeCreate = LocalDateTime.now();
        long millisCreateDate = timeCreate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        LocalDateTime timeEnd = timeCreate.plusDays(certificateDTO.getDuration());
        long millisEndDate = timeEnd.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        Certificate certificate = certificateDtoMapper.changeDtoToCertificate(certificateDTO);
        certificate.setCreateDate(millisCreateDate);
        certificate.setLastUpdateDate(millisEndDate);

        certificateDAO.create(certificate);
    }

    @Override
    public CertificateDTO getCertificate(int id) {
        return certificateDtoMapper.changeCertificateToDto(certificateDAO.certificateById(id),
                tagService.getTagsByCertificateId(id));
    }

    @Override
    public void deleteCertificate(int id) {
        certificateDAO.delete(id);
    }

    @Override
    public void update(CertificateDTO certificateDTO) {
        Certificate certificate = certificateDtoMapper.changeDtoToCertificate(certificateDTO);

        for (TagDTO tagDTO : certificateDTO.getTagList()) {
            tagService.createTag(tagDTO);
        }

        certificateDAO.update(certificate.getId(), certificate);
    }

    @Override
    public List<CertificateDTO> getCertificatesByTagId(int tagId) {
        List<Certificate> certificates = certificateDAO.getCertificatesByTagId(tagId);
        return certificates
                .stream()
                .map(certificate -> {
                    List<TagDTO> tags = tagService.getTagsByCertificateId(certificate.getId());
                    return certificateDtoMapper.changeCertificateToDto(certificate, tags);
                })
                .collect(Collectors.toList());
    }


}
