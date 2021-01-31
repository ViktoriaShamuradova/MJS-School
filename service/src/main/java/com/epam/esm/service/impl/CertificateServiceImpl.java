package com.epam.esm.service.impl;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.persistence.CertificateDAO;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.entitydtomapper.CertificateDtoMapper;
import com.epam.esm.service.exception.NoSuchResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CertificateServiceImpl implements CertificateService {

    private final CertificateDAO certificateDAO;
    private final TagService tagService;
    private final CertificateDtoMapper certificateDtoMapper;

    @Autowired
    public CertificateServiceImpl(CertificateDAO certificateDAO,
                                  CertificateDtoMapper certificateDtoMapper, TagService tagService) {
        this.certificateDAO = certificateDAO;
        this.certificateDtoMapper = certificateDtoMapper;
        this.tagService = tagService;
    }

    @Override
    public List<CertificateDTO> findByPartOfNameOrDescription(String partOfNameOrDescription) {
        return getListCertificateDto(certificateDAO.findByPartOfNameOrDescription(partOfNameOrDescription));
    }

    @Override
    public List<CertificateDTO> findByTagId(long id) {
        tagService.find(id);
        return getListCertificateDto(certificateDAO.findCertificateByTagId(id));
    }

    @Override
    public void addLinkCertificateWithTags(long certificateId, long tagId) {
        certificateDAO.addLinkCertificateWithTags(certificateId, tagId);
    }

    @Override
    public List<CertificateDTO> findAll() {
        return getListCertificateDto(certificateDAO.findAll());
    }

    @Override
    public CertificateDTO create(CertificateDTO certificateDTO) {
        certificateDTO.setCreateDate(Instant.now().truncatedTo(ChronoUnit.MICROS));
        certificateDTO.setUpdateLastDate(Instant.now().truncatedTo(ChronoUnit.MICROS));
        certificateDAO.create(certificateDtoMapper.changeDtoToCertificate(certificateDTO));

        if (certificateDTO.getTagList() != null) {
            for (Tag tag : certificateDTO.getTagList()) {
                Tag tagDTONew = tagService.create(tag);
                if (tagDTONew != null) {
                    addLinkCertificateWithTags(certificateDTO.getId(), tagDTONew.getId());
                }
            }
        }
        return certificateDTO;
    }

    @Override
    public CertificateDTO find(Long id) {
        Certificate certificate = certificateDAO.find(id).orElseThrow(() -> new NoSuchResourceException(".cert","id= "+id));
        return certificateDtoMapper.changeCertificateToDto(certificate, tagService.findByCertificateId(id));
    }

    @Override
    public void delete(Long id) {
        certificateDAO.find(id).orElseThrow(() -> new NoSuchResourceException(".cert","id= " + id));
        certificateDAO.delete(id);
    }

    @Override
    public void update(CertificateDTO certificateDTO) {
        if (!isFieldsChanged(certificateDTO)) {
            return;
        }
        certificateDAO.find(certificateDTO.getId()).orElseThrow(() -> new NoSuchResourceException(".cert", "id= "+ certificateDTO.getId()));
        certificateDTO.setUpdateLastDate(Instant.now());

        if (certificateDTO.getTagList() != null) {
            for (Tag tag : certificateDTO.getTagList()) {
                tagService.create(tag);
            }
        }
        certificateDAO.update(certificateDtoMapper.changeDtoToCertificate(certificateDTO));
    }

    private List<CertificateDTO> getListCertificateDto(List<Certificate> certificates) {
        return certificates
                .stream()
                .map(certificate -> {
                    List<Tag> tags = tagService.findByCertificateId(certificate.getId());
                    return certificateDtoMapper.changeCertificateToDto(certificate, tags);
                })
                .collect(Collectors.toList());
    }

    private boolean isFieldsChanged(CertificateDTO certificateDTO) {
        if (certificateDTO.getName() != null) return true;
        if (certificateDTO.getDescription() != null) return true;
        if (certificateDTO.getPrice() != null) return true;
        if (certificateDTO.getDuration() != 0) return true;
        return certificateDTO.getTagList() != null;
    }

}
