package com.epam.esm.service.impl;

import com.epam.esm.criteria_info.CertificateCriteriaInfo;
import com.epam.esm.criteria_info.PageInfo;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.CertificateUpdateDto;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.persistence.CertificateDAO;
import com.epam.esm.persistence.TagDAO;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.entitydtomapper.impl.CertificateDtoMapper;
import com.epam.esm.service.exception.ExceptionCode;
import com.epam.esm.service.exception.NoSuchResourceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CertificateServiceImpl implements CertificateService {

    private final CertificateDAO certificateDAO;
    private final TagDAO tagDAO;
    private final CertificateDtoMapper certificateDtoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CertificateDTO> find(PageInfo pageInfo, CertificateCriteriaInfo criteriaInfo) {
        List<Certificate> certificates = certificateDAO.findAll(pageInfo, criteriaInfo);
        return getListCertificateDto(certificates);
    }

    @Transactional
    @Override
    public CertificateDTO create(CertificateDTO certificateDTO) {
        certificateDTO.setCreateDate(Instant.now().truncatedTo(ChronoUnit.MICROS));
        certificateDTO.setUpdateLastDate(Instant.now().truncatedTo(ChronoUnit.MICROS));

        Set<Tag> tags = prepareTags(certificateDTO.getTags());
        Certificate certificate = certificateDtoMapper.changeToEntity(certificateDTO);
        certificate.setTags(tags);
        certificate.setId(null);

        certificateDTO.setId(certificateDAO.create(certificate));
        return certificateDTO;
    }

    @Transactional(readOnly = true)
    @Override
    public CertificateDTO findById(Long id) {
        Certificate certificate = certificateDAO.find(id).orElseThrow(() ->
                new NoSuchResourceException(ExceptionCode.NO_SUCH_CERTIFICATE_FOUND.getErrorCode(), "id= " + id));
        return certificateDtoMapper.changeToDto(certificate);
    }

    @Transactional
    @Override
    public boolean delete(Long id) {
        Optional<Certificate> certificate = certificateDAO.find(id);
        if (certificate.isPresent()) {
            certificateDAO.delete(certificate.get());
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public CertificateDTO update(CertificateUpdateDto certificateUpdateDto, Long id) {
        Certificate certificate = certificateDAO.find(id).orElseThrow(() ->
                new NoSuchResourceException(ExceptionCode.NO_SUCH_CERTIFICATE_FOUND.getErrorCode(), "id= " + certificateUpdateDto.getId().get()));

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
            certificate.setTags(prepareTags(certificateUpdateDto.getTags().get()));
        }
        certificate.setUpdateLastDate(Instant.now());

        certificateDAO.update(certificate);
        return certificateDtoMapper.changeToDto(certificate);
    }

    private Set<Tag> prepareTags(Set<TagDTO> tagDTOS) {
        Set<Tag> tags = new HashSet<>();
        tagDTOS.forEach((tagDTO -> {
            Tag tag = new Tag(tagDTO.getName());
            Optional<Tag> tagFromDB = tagDAO.find(tagDTO.getName());
            if (tagFromDB.isPresent()) {
                tag = tagFromDB.get();
            } else {
                tag.setId(tagDAO.create(tag));
            }
            tags.add(tag);
        }));
        return tags;
    }

    @Transactional
    @Override
    public long getCount() {
        return certificateDAO.getCount();
    }

    private List<CertificateDTO> getListCertificateDto(List<Certificate> certificates) {
        return certificates
                .stream()
                .map(certificateDtoMapper::changeToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CertificateDTO update(CertificateDTO certificateDTO) {
        throw new UnsupportedOperationException();
    }
}
