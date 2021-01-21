package com.epam.esm.impl;

import com.epam.esm.CertificateDAO;
import com.epam.esm.CertificateService;
import com.epam.esm.TagService;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entitydtomapper.CertificateDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
        return getListCertificateDto(certificateDAO.allCertificates());
    }

    @Transactional
    @Override
    public void saveCertificate(CertificateDTO certificateDTO) {
        LocalDateTime timeCreate = LocalDateTime.now();
        LocalDateTime timeEnd = timeCreate.plusDays(certificateDTO.getDuration());
        certificateDTO.setCreateDate(timeCreate);
        certificateDTO.setUpdateLastDate(timeEnd);
        certificateDAO.create(certificateDtoMapper.changeDtoToCertificate(certificateDTO));

        for (TagDTO tagDTO : certificateDTO.getTagList()) {
            TagDTO tagDTONew = tagService.createTag(tagDTO);
            if (tagDTONew != null) {
                certificateDAO.addCertificateTag(certificateDTO.getId(), tagDTONew.getId());
            }
        }
    }

    @Nullable
    @Override
    public CertificateDTO getCertificate(int id) {
        Certificate certificate = certificateDAO.certificateById(id);
        if (certificate != null) {
            return certificateDtoMapper.changeCertificateToDto(certificate, tagService.getTagsByCertificateId(id));
        }
        return null;
    }

    @Override
    public void deleteCertificate(int id) {
        certificateDAO.delete(id);
    }

    @Transactional
    @Override
    public void update(CertificateDTO certificateDTO, int certificateId) {
        Certificate certificateFromDb = certificateDAO.certificateById(certificateId);
        if (certificateDTO != null) {
            if (certificateDTO.getName() != null) {
                certificateFromDb.setName(certificateDTO.getName());
            }
            if (certificateDTO.getDescription() != null) {
                certificateFromDb.setDescription(certificateDTO.getDescription());
            }
            if (certificateDTO.getPrice() != null) {
                certificateFromDb.setPrice(certificateDTO.getPrice());
            }
            if (certificateDTO.getDuration() != 0) {
                certificateFromDb.setDuration(certificateDTO.getDuration());
            }
            if (certificateDTO.getCreateDate() != null) {
                certificateFromDb.setCreateDate(certificateDTO.getCreateDate());
            }
            LocalDateTime currentDate = LocalDateTime.now();
            certificateFromDb.setUpdateLastDate(currentDate);

            if (certificateDTO.getTagList() != null) {
                for (TagDTO tagDTO : certificateDTO.getTagList()) {
                    tagService.createTag(tagDTO);
                }
            }
            certificateDAO.update(certificateId, certificateFromDb);
        }
    }

    @Override
    public List<CertificateDTO> getCertificatesByTagId(int tagId) {
        return getListCertificateDto(certificateDAO.getCertificatesByTagId(tagId));
    }

    @Override
    public List<CertificateDTO> getCertificatesByPartOfNameOrDescription(CertificateDTO certificateDTO) {
        return getListCertificateDto(certificateDAO.getCertificatesByPartOfNameOrDescription(certificateDTO.getName(), certificateDTO.getDescription()))
        ;
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
