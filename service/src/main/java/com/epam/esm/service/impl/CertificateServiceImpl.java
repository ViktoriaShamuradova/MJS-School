package com.epam.esm.service.impl;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.persistence.CertificateDAO;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.CertificateTagService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.entitydtomapper.CertificateDtoMapper;
import com.epam.esm.service.exception.NoSuchResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CertificateServiceImpl implements CertificateService {

    private CertificateDAO certificateDAO;
    private TagService tagService;
    private CertificateDtoMapper certificateDtoMapper;
    private final CertificateTagService certificateTagService;

    @Autowired
    public CertificateServiceImpl(CertificateDAO certificateDAO,
                                  CertificateTagService certificateTagService,
                                  CertificateDtoMapper certificateDtoMapper) {
        this.certificateDAO = certificateDAO;
        this.certificateDtoMapper = certificateDtoMapper;
        this.certificateTagService = certificateTagService;
    }

    @Autowired
    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CertificateDTO> findAll() {
        return getListCertificateDto(certificateDAO.findAll());
    }

    @Transactional
    @Override
    public void create(CertificateDTO certificateDTO) {
        certificateDTO.setCreateDate(Instant.now().truncatedTo(ChronoUnit.MICROS));
        certificateDTO.setUpdateLastDate(Instant.now().truncatedTo(ChronoUnit.MICROS));
        certificateDAO.create(certificateDtoMapper.changeDtoToCertificate(certificateDTO));

        if (certificateDTO.getTagList() != null) {
            for (TagDTO tagDTO : certificateDTO.getTagList()) {
                TagDTO tagDTONew = tagService.createTag(tagDTO);
                if (tagDTONew != null) {
                    certificateTagService.add(certificateDTO.getId(), tagDTONew.getId());
                }
            }
        }
    }

    @Nullable
    @Override
    @Transactional(readOnly = true)
    public CertificateDTO find(int id) {
        Certificate certificate = certificateDAO.find(id).orElseThrow(() -> new NoSuchResourceException());
        return certificateDtoMapper.changeCertificateToDto(certificate, tagService.getTagsByCertificateId(id));
    }

    @Override
    public void delete(int id) {
        CertificateDTO certificate = find(id);
        if (certificate == null) {
            throw new NoSuchResourceException("There is no certificate with this id = " + id + " in dataBase");
        }
        certificateDAO.delete(id);
    }

    @Transactional
    @Override
    public void update(CertificateDTO certificateDTO) {
        certificateDAO.find(certificateDTO.getId()).orElseThrow(() -> new NoSuchResourceException());

//            if (certificateDTO.getName() != null) {
//                certificateFromDb.setName(certificateDTO.getName());
//            }
//            if (certificateDTO.getDescription() != null) {
//                certificateFromDb.setDescription(certificateDTO.getDescription());
//            }
//            if (certificateDTO.getPrice() != null) {
//                certificateFromDb.setPrice(certificateDTO.getPrice());
//            }
//            if (certificateDTO.getDuration() != 0) {
//                certificateFromDb.setDuration(certificateDTO.getDuration());
//            }
//            if (certificateDTO.getCreateDate() != null) {
//                certificateFromDb.setCreateDate(certificateDTO.getCreateDate());
//            }
//            certificateFromDb.setUpdateLastDate(Instant.now());
//
//            if (certificateDTO.getTagList() != null) {
//                for (TagDTO tagDTO : certificateDTO.getTagList()) {
//                    tagService.createTag(tagDTO);
//                }
//            }
//            certificateDAO.update(certificateId, certificateFromDb);

   }

    @Override
    @Transactional(readOnly = true)
    public List<CertificateDTO> findByTagId(long tagId) {
        return getListCertificateDto(certificateDAO.findByTagId(tagId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CertificateDTO> findByPartOfNameOrDescription(String partOfNameOrDescription) {
        System.out.println("Here");
        return getListCertificateDto(certificateDAO.findByPartOfNameOrDescription(partOfNameOrDescription));
    }

    private List<CertificateDTO> getListCertificateDto(List<Certificate> certificates) {
        return certificates
                .stream()
                .map(certificate -> {
                    List<TagDTO> tags = tagService.getTagsByCertificateId(certificate.getId());
                    return certificateDtoMapper.changeCertificateToDto(certificate, tags);
                })
                .collect(Collectors.toList());
    }
}
