package com.epam.esm.service.impl;

import com.epam.esm.service.CertificateService;
import com.epam.esm.persistence.TagDAO;
import com.epam.esm.service.TagService;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.entitydtomapper.TagDtoMapper;
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
    public List<TagDTO> getAllTags() {
        List<Tag> tags = tagDAO.findAll();

        return tags.stream().map(tag -> {
            List<CertificateDTO> certificatesDTO = certificateService.findByTagId(tag.getId());
            return tagDtoMapper.changeTagToTagDTO(tag, certificatesDTO);
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteTag(long tagId) {
        tagDAO.delete(tagId);
    }

    @Nullable
    @Override
    public TagDTO createTag(TagDTO tagDTO) {
        if (tagExist(tagDTO) == null) {
            int tagId = tagDAO.add(new Tag(tagDTO.getName()));
            List<CertificateDTO> certificatesDTO = certificateService.findByTagId(tagId);
            return tagDtoMapper.changeTagToTagDTO(tagDAO.find(tagId), certificatesDTO);
        }
        return null;
    }

    @Nullable
    @Override
    @Transactional(readOnly = true)
    public TagDTO getTag(long tagId) {
        Tag tag = tagDAO.find(tagId);
        if (tag != null) {
            List<CertificateDTO> certificates = certificateService.findByTagId(tag.getId());
            return tagDtoMapper.changeTagToTagDTO(tag, certificates);
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagDTO> getTagsByCertificateId(long certificateId) {
        List<Tag> tags = tagDAO.findByCertificateId(certificateId);
        return tags.stream().map(tag -> tagDtoMapper.changeTagToTagDTO(tag)).collect(Collectors.toList());
    }

    @Nullable
    @Override
    @Transactional(readOnly = true)
    public TagDTO getTag(String name) {
        Tag tag = tagDAO.find(name);
        if (tag != null) {
            List<CertificateDTO> certificates = certificateService.findByTagId(tag.getId());
            return tagDtoMapper.changeTagToTagDTO(tag, certificates);
        }
        return null;
    }

    @Nullable
    private Tag tagExist(TagDTO tagDTO) {
        Tag tag = tagDAO.find(tagDTO.getName());
        if (tag != null) {
            return tag;
        }
        return null;
    }
}
