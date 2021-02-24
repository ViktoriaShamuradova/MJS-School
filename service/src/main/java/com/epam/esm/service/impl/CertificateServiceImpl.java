package com.epam.esm.service.impl;

import com.epam.esm.criteria_info.CertificateCriteriaInfo;
import com.epam.esm.criteria_info.PageInfo;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.CertificateUpdateDto;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.persistence.CertificateDAO;
import com.epam.esm.persistence.specification.Specification;
import com.epam.esm.persistence.specification_builder.SpecificationBuilder;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.entitydtomapper.impl.CertificateDtoMapper;
import com.epam.esm.service.entitydtomapper.impl.TagMapper;
import com.epam.esm.service.exception.ExceptionCode;
import com.epam.esm.service.exception.NoSuchResourceException;
import com.epam.esm.service.exception.NotSupportedException;
import com.epam.esm.service.validate.PaginationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CertificateServiceImpl implements CertificateService {

    private final CertificateDAO certificateDAO;
    private final TagService tagService;
    private final CertificateDtoMapper certificateDtoMapper;
    private final SpecificationBuilder specificationBuilder;
    private final PaginationValidator paginationValidator;

    @Autowired
    public CertificateServiceImpl(CertificateDAO certificateDAO,
                                  CertificateDtoMapper certificateDtoMapper,
                                  TagService tagService,
                                  SpecificationBuilder specificationBuilder,
                                  PaginationValidator paginationValidator) {
        this.certificateDAO = certificateDAO;
        this.certificateDtoMapper = certificateDtoMapper;
        this.tagService = tagService;
        this.specificationBuilder = specificationBuilder;
        this.paginationValidator = paginationValidator;
    }


//    @Override
//    public List<CertificateDTO> findByTagId(long id) {
//        tagService.find(id);
//        return getListCertificateDto(certificateDAO.findByTagId(id));
//    }

    //переименовать
    @Override
    public List<CertificateDTO> find(PageInfo pageInfo, CertificateCriteriaInfo criteriaInfo) {
        paginationValidator.validate(pageInfo);
        List<Specification> specifications = specificationBuilder.build(criteriaInfo);
        List<Certificate> certificates = certificateDAO.findAll(specifications, pageInfo.getOffset(), pageInfo.getLimit());
        return getListCertificateDto(certificates);
    }

    @Override
    public CertificateDTO create(CertificateDTO certificateDTO) {
        certificateDTO.setCreateDate(Instant.now().truncatedTo(ChronoUnit.MICROS));
        certificateDTO.setUpdateLastDate(Instant.now().truncatedTo(ChronoUnit.MICROS));
        long id = certificateDAO.create(certificateDtoMapper.changeToEntity(certificateDTO));
        certificateDTO.setId(id);

        if (certificateDTO.getTags() != null) {
            certificateDTO.getTags().forEach(tagService::create);
        }

        return certificateDTO;
    }

    @Override
    public CertificateDTO findById(Long id) {
        Certificate certificate = certificateDAO.find(id).orElseThrow(() ->
                new NoSuchResourceException(ExceptionCode.NO_SUCH_CERTIFICATE_FOUND.getErrorCode(), "id= " + id));
        return certificateDtoMapper.changeToDto(certificate);
    }

    @Override
    public boolean delete(Long id) {
        Optional<Certificate> certificate = certificateDAO.find(id);
        if (certificate.isPresent()) {
            certificateDAO.delete(certificate.get());
            return true;
        }
        return false;
    }

    @Override
    public CertificateDTO update(CertificateUpdateDto certificateUpdateDto) {
        Certificate certificate = certificateDAO.find(certificateUpdateDto.getId().get()).orElseThrow(() ->
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
        TagMapper tagMapper = new TagMapper();
        tagDTOS.forEach((tagDTO -> {
            tagService.create(tagDTO);
            tags.add(tagMapper.changeToEntity(tagDTO));
        }));
        return tags;
    }


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
        throw new NotSupportedException(ExceptionCode.NOT_SUPPORTED_OPERATION.getErrorCode());
    }
}
