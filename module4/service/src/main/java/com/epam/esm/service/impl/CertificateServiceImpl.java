package com.epam.esm.service.impl;

import com.epam.esm.criteria_info.CertificateCriteriaInfo;
import com.epam.esm.criteria_info.PageInfo;
import com.epam.esm.criteria_info.TagCriteriaInfo;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.CertificateUpdateDto;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.metamodel.Certificate_;
import com.epam.esm.persistence.CertificateDAO;
import com.epam.esm.persistence.TagDAO;
import com.epam.esm.persistence.dataspecification.CertificateSpecification;
import com.epam.esm.persistence.dataspecification.TagSpecification;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.exception.ExceptionCode;
import com.epam.esm.service.exception.NoSuchResourceException;
import com.epam.esm.service.modelmapper.CertificateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
    private final CertificateMapper mapper;


    @Transactional(readOnly = true)
    @Override
    public CertificateDTO findById(Long id) {
        Certificate certificate = certificateDAO.findById(id).orElseThrow(() ->
                new NoSuchResourceException(ExceptionCode.NO_SUCH_CERTIFICATE_FOUND.getErrorCode(), "id= " + id));
        return mapper.toDTO(certificate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CertificateDTO> find(PageInfo pageInfo, CertificateCriteriaInfo criteriaInfo) {
        CertificateSpecification certificateSpecification = new CertificateSpecification(criteriaInfo);
        Sort sort = createSort(criteriaInfo);

        return certificateDAO.findAll(certificateSpecification, PageRequest.of(pageInfo.getCurrentPage(),
                pageInfo.getLimit(), sort))
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public CertificateDTO create(CertificateDTO certificateDTO) {
        certificateDTO.setCreateDate(Instant.now().truncatedTo(ChronoUnit.MICROS));
        certificateDTO.setUpdateLastDate(Instant.now().truncatedTo(ChronoUnit.MICROS));

        Set<Tag> tags = prepareTags(certificateDTO.getTags());
        Certificate certificate = mapper.toEntity(certificateDTO);
        certificate.setTags(tags);
        certificate.setId(null);

        return mapper.toDTO(certificateDAO.save(certificate));
    }

    @Transactional
    @Override
    public boolean delete(Long id) {
        Optional<Certificate> certificate = certificateDAO.findById(id);
        if (certificate.isPresent()) {
            certificateDAO.delete(certificate.get());
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public CertificateDTO update(CertificateUpdateDto certificateUpdateDto, Long id) {
        Certificate certificate = certificateDAO.findById(id).orElseThrow(() ->
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

        return mapper.toDTO(certificateDAO.save(certificate));
    }

    private Set<Tag> prepareTags(Set<TagDTO> tagDTOS) {
        Set<Tag> tags = new HashSet<>();
        tagDTOS.forEach((tagDTO -> {
            Tag tag = new Tag(tagDTO.getName());
            TagCriteriaInfo criteriaInfo = new TagCriteriaInfo();
            criteriaInfo.setName(tag.getName());
            Optional<Tag> tagFromDB = tagDAO.findOne(new TagSpecification(criteriaInfo));
            if (tagFromDB.isPresent()) {
                tag = tagFromDB.get();
            } else {
                tagDAO.save(tag);
            }
            tags.add(tag);
        }));
        return tags;
    }

    @Transactional
    @Override
    public long getCount() {
        return certificateDAO.count();
    }

    @Override
    public CertificateDTO update(CertificateDTO certificateDTO) {
        throw new UnsupportedOperationException();
    }

    private Sort createSort(CertificateCriteriaInfo criteriaInfo) {
        String orderBy = criteriaInfo.getOrderBy();
        if (orderBy == null) {
            return Sort.by(Certificate_.ID).ascending();
        }
        List<Sort.Order> orders = devideParts(orderBy);

        return Sort.by(orders);
    }

    private List<Sort.Order> devideParts(String orderBy) {
        List<Sort.Order> orders = new ArrayList<>();

        if (orderBy.contains(",")) {
            String[] parts = orderBy.split(",");
            for (String part : parts) {
                orders.add(oneSort(part));
            }
            return orders;
        }
        orders.add(oneSort(orderBy));
        return orders;
    }

    private Sort.Order oneSort(String orderBy) {
        if (orderBy.startsWith("-")) {
            return new Sort.Order(Sort.Direction.DESC, orderBy.substring(1));
        }
        return new Sort.Order(Sort.Direction.ASC, orderBy.substring(1));
    }
}
