package com.epam.esm.service.impl;

import com.epam.esm.persistence.CertificateDAO;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.service.entitydtomapper.CertificateDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
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

    @Autowired
    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    @Override
    public List<CertificateDTO> getAllCertificates() {
        return getListCertificateDto(certificateDAO.allCertificates());
    }

    @Transactional
    @Override
    public void saveCertificate(CertificateDTO certificateDTO) {
        certificateDTO.setCreateDate(Instant.now().truncatedTo( ChronoUnit.MICROS ));
        certificateDTO.setUpdateLastDate(Instant.now().truncatedTo( ChronoUnit.MICROS));
        certificateDAO.create(certificateDtoMapper.changeDtoToCertificate(certificateDTO));

        if(certificateDTO.getTagList()!=null) {
            for (TagDTO tagDTO : certificateDTO.getTagList()) {
                TagDTO tagDTONew = tagService.createTag(tagDTO);
                if (tagDTONew != null) {
                    certificateDAO.addCertificateTag(certificateDTO.getId(), tagDTONew.getId());
                }
            }
            System.out.println("execute");
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
    public void update(CertificateDTO certificateDTO, long certificateId) {
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
            certificateFromDb.setUpdateLastDate(Instant.now());

            if (certificateDTO.getTagList() != null) {
                for (TagDTO tagDTO : certificateDTO.getTagList()) {
                    tagService.createTag(tagDTO);
                }
            }
            certificateDAO.update(certificateId, certificateFromDb);
        }
    }

    @Override
    public List<CertificateDTO> getCertificatesByTagId(long tagId) {
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
