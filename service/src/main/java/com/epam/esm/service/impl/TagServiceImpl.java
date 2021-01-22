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


import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    private TagDAO tagDAO;
    private TagDtoMapper tagDtoMapper;
    @Autowired
    private CertificateService certificateService;

    @Autowired
    public TagServiceImpl(TagDAO tagDAO, TagDtoMapper tagDtoMapper) {
        this.tagDAO = tagDAO;
        this.tagDtoMapper = tagDtoMapper;
    }

    @Override
    public List<TagDTO> getAllTags() {
        List<Tag> tags = tagDAO.findAllTagList();

        return tags.stream().map(tag -> {
            List<CertificateDTO> certificatesDTO = certificateService.getCertificatesByTagId(tag.getId());
            return tagDtoMapper.changeTagToTagDTO(tag, certificatesDTO);
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteTag(long tagId) {
        tagDAO.deleteTag(tagId);
    }

    @Nullable
    @Override
    public TagDTO createTag(TagDTO tagDTO) {
        if (tagExist(tagDTO) == null) {
            int tagId = tagDAO.addNewTag(new Tag(tagDTO.getName()));
            List<CertificateDTO> certificatesDTO = certificateService.getCertificatesByTagId(tagId);
            return tagDtoMapper.changeTagToTagDTO(tagDAO.findTagById(tagId), certificatesDTO);
        }
        return null;
    }

    @Nullable
    @Override
    public TagDTO getTag(long tagId) {
        Tag tag = tagDAO.findTagById(tagId);
        if (tag != null) {
            List<CertificateDTO> certificates = certificateService.getCertificatesByTagId(tag.getId());
            return tagDtoMapper.changeTagToTagDTO(tag, certificates);
        }
        return null;
    }

    @Override
    public List<TagDTO> getTagsByCertificateId(long certificateId) {
        List<Tag> tags = tagDAO.findTagByCertificateId(certificateId);
        return tags.stream().map(tag -> tagDtoMapper.changeTagToTagDTO(tag)).collect(Collectors.toList());
    }

    @Nullable
    @Override
    public TagDTO getTag(String name) {
        Tag tag = tagDAO.findTagByName(name);
        if (tag != null) {
            List<CertificateDTO> certificates = certificateService.getCertificatesByTagId(tag.getId());
            return tagDtoMapper.changeTagToTagDTO(tag, certificates);
        }
        return null;
    }

    @Nullable
    private Tag tagExist(TagDTO tagDTO) {
        Tag tag = tagDAO.findTagByName(tagDTO.getName());
        if (tag != null) {
            return tag;
        }
        return null;
    }
}
