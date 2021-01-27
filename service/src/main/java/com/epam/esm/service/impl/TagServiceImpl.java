package com.epam.esm.service.impl;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.persistence.TagDAO;
import com.epam.esm.service.CertificateTagService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.entitydtomapper.TagDtoMapper;
import com.epam.esm.service.exception.NoSuchResourceException;
import com.epam.esm.service.exception.TagAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    private final TagDAO tagDAO;
    private final TagDtoMapper tagDtoMapper;
    private final CertificateTagService certificateTagService;

    @Autowired
    public TagServiceImpl(TagDAO tagDAO, TagDtoMapper tagDtoMapper, CertificateTagService certificateTagService) {
        this.tagDAO = tagDAO;
        this.tagDtoMapper = tagDtoMapper;
        this.certificateTagService = certificateTagService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagDTO> findAll() {
        List<Tag> tags = tagDAO.findAll();

        return tags.stream().map(tag -> {
            List<CertificateDTO> certificatesDTO = certificateTagService.findCertificateByTagId(tag.getId());
            return tagDtoMapper.changeTagToTagDTO(tag, certificatesDTO);
        }).collect(Collectors.toList());
    }

    @Override
    public void delete(long id) {
        tagDAO.find(id).orElseThrow(() -> new NoSuchResourceException());
        tagDAO.delete(id);
    }

    @Override
    public TagDTO create(TagDTO tagDTO) {
        if (tagDAO.find(tagDTO.getName()).isPresent())
            throw new TagAlreadyExistsException();
        tagDAO.add(tagDTO);
        return tagDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public TagDTO find(String name) {
        Tag tag = tagDAO.find(name).orElseThrow(() -> new NoSuchResourceException( ));
        List<CertificateDTO> certificates =
        certificateTagService.findCertificateByTagId(tag.getId());
        return tagDtoMapper.changeTagToTagDTO(tag, certificates);
    }
}
