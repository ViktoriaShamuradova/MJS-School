package com.epam.esm.service.impl;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.persistence.TagDAO;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.entitydtomapper.TagDtoMapper;
import com.epam.esm.service.exception.NoSuchResourceException;
import com.epam.esm.service.exception.TagAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    private TagDAO tagDAO;
    private TagDtoMapper tagDtoMapper;
    private final CertificateService certificateService;

    @Autowired
    public TagServiceImpl(TagDAO tagDAO, TagDtoMapper tagDtoMapper, CertificateService certificateService) {
        this.tagDAO = tagDAO;
        this.tagDtoMapper = tagDtoMapper;
        this.certificateService = certificateService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagDTO> findAll() {
        List<Tag> tags = tagDAO.findAll();

        return tags.stream().map(tag -> {
            List<CertificateDTO> certificatesDTO = certificateService.findByTagId(tag.getId());
            return tagDtoMapper.changeTagToTagDTO(tag, certificatesDTO);
        }).collect(Collectors.toList());
    }

    @Override
    public void delete(long id) {
        tagDAO.find(id).orElseThrow(() -> new NoSuchResourceException());
        tagDAO.delete(id);
    }

    @Override
    public void create(TagDTO tagDTO) {
        if (tagDAO.find(tagDTO.getName()).isPresent())
            throw new TagAlreadyExistsException("such tag with name = " + tagDTO.getName() + " already exists in dataBase");
        tagDAO.add(tagDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public TagDTO find(long id) {
        Tag tag = tagDAO.find(id).orElseThrow(() -> new NoSuchResourceException("There is no tag with this id = " + id + " in dataBase"));
        List<CertificateDTO> certificates = certificateService.findByTagId(id);
        return tagDtoMapper.changeTagToTagDTO(tag, certificates);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagDTO> findByCertificateId(long certificateId) {
        List<Tag> tags = tagDAO.findByCertificateId(certificateId);
        return tags.stream().map(tag -> tagDtoMapper.changeTagToTagDTO(tag)).collect(Collectors.toList());
    }

    @Nullable
    @Override
    @Transactional(readOnly = true)
    public TagDTO find(String name) {
        Tag tag = tagDAO.find(name).orElseThrow(() -> new NoSuchResourceException("There is no tag with this name = " + name + " in dataBase"));
        List<CertificateDTO> certificates = certificateService.findByTagId(tag.getId());
        return tagDtoMapper.changeTagToTagDTO(tag, certificates);
    }
}
