package com.epam.esm.service.impl;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.CertificateUpdateDto;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.persistence.CertificateDAO;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.entitydtomapper.CertificateDtoMapper;
import com.epam.esm.service.exception.ExceptionCode;
import com.epam.esm.service.exception.NoSuchResourceException;
import com.epam.esm.service.exception.NotSupportedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
        return getListCertificateDto(certificateDAO.findByTagId(id));
    }

    @Override
    public void addLinkCertificateWithTags(long certificateId, long tagId) {
        certificateDAO.addLinkCertificateWithTags(certificateId, tagId);
    }


    @Override
    public List<CertificateDTO> findAll(Map<String, String> params) {
        int pageNumber = Integer.parseInt(params.get("current page"));
        int limit = Integer.parseInt(params.get("limit"));
        long id = ((long) pageNumber * limit) - limit;
        return getListCertificateDto(certificateDAO.findAll(id, limit));
    }

    @Override
    public CertificateDTO create(CertificateDTO certificateDTO) {
        Certificate certificate = certificateDAO.create(certificateDtoMapper.changeDtoToCertificate(certificateDTO)).get();
        certificate.setCreateDate(Instant.now().truncatedTo(ChronoUnit.MICROS));
        certificate.setUpdateLastDate(Instant.now().truncatedTo(ChronoUnit.MICROS));
        if (certificateDTO.getTags() != null) {
            for (TagDTO tag : certificateDTO.getTags()) {
                TagDTO tagNew = tagService.create(tag);
                addLinkCertificateWithTags(certificate.getId(), tagNew.getId());
            }
        }
        return certificateDtoMapper.changeCertificateToDto(certificate, new HashSet<>(tagService.findByCertificateId(certificate.getId())));
    }

    @Override
    public CertificateDTO find(Long id) {
        Certificate certificate = certificateDAO.find(id).orElseThrow(() ->
                new NoSuchResourceException(ExceptionCode.NO_SUCH_CERTIFICATE_FOUND.getErrorCode(), "id= " + id));
        return certificateDtoMapper.changeCertificateToDto(certificate, new HashSet<>(tagService.findByCertificateId(id)));
    }

    @Override
    public void delete(Long id) {
        certificateDAO.find(id).orElseThrow(() ->
                new NoSuchResourceException(ExceptionCode.NO_SUCH_CERTIFICATE_FOUND.getErrorCode(), "id= " + id));
        certificateDAO.delete(id);
    }

    @Override
    public CertificateDTO update(CertificateUpdateDto certificateUpdateDto, long id) {
        Certificate certificate = certificateDAO.find(id).orElseThrow(() ->
                new NoSuchResourceException(ExceptionCode.NO_SUCH_CERTIFICATE_FOUND.getErrorCode(), "id= " + id));

        if (certificateUpdateDto.getName().isPresent()) {
            certificate.setName(certificateUpdateDto.getName().get());
        }
        if (certificateUpdateDto.getDescription().isPresent()) {
            certificate.setDescription(certificateUpdateDto.getDescription().get());
        }
        if (certificateUpdateDto.getPrice().isPresent()) {
            certificate.setPrice(certificateUpdateDto.getPrice().get());
        }
        if (certificateUpdateDto.getDuration().isPresent()) {
            certificate.setDuration(certificateUpdateDto.getDuration().get());
        }

        if (certificateUpdateDto.getTags().isPresent()) {
            certificateDAO.deleteAllLinksWithTags(id);
            for (TagDTO tag : certificateUpdateDto.getTags().get()) {
                TagDTO tagCreated = tagService.create(tag);
                certificateDAO.addLinkCertificateWithTags(id, tagCreated.getId());
            }
        }
        certificate.setUpdateLastDate(Instant.now());

        certificateDAO.update(certificate);
        return certificateDtoMapper.changeCertificateToDto(certificateDAO.find(id).get(), new HashSet<>(tagService.findByCertificateId(id)));
    }

    @Override
    public List<CertificateDTO> findByTags(List<String> tagNames) {
        return getListCertificateDto(certificateDAO.findByTagNames(tagNames));
    }

    @Override
    public long getCount() {
        return certificateDAO.getCount();
    }

    private List<CertificateDTO> getListCertificateDto(List<Certificate> certificates) {
        return certificates
                .stream()
                .map(certificate -> {
                    List<TagDTO> tags = tagService.findByCertificateId(certificate.getId());
                    return certificateDtoMapper.changeCertificateToDto(certificate, new HashSet<>(tags));
                })
                .collect(Collectors.toList());
    }

    @Override
    public CertificateDTO update(CertificateDTO certificateDTO) {
        throw new NotSupportedException(ExceptionCode.NOT_SUPPORTED_OPERATION.getErrorCode());
    }

}
