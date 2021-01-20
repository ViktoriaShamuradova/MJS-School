package com.epam.esm.impl;

import com.epam.esm.CertificateService;
import com.epam.esm.TagDAO;
import com.epam.esm.TagService;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.entitydtomapper.TagDtoMapper;
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
    public void deleteTag(int tagId) {

    }

    @Override
    public TagDTO createTag(TagDTO tagDTO) {
        if (tagExist(tagDTO) == null) {
            int tagId = tagDAO.addNewTag(new Tag(tagDTO.getName()));
            List<CertificateDTO> certificatesDTO = certificateService.getCertificatesByTagId(tagId);
            return tagDtoMapper.changeTagToTagDTO(tagDAO.findTagById(tagId), certificatesDTO);
        }
        return null;

    }

    @Override
    public TagDTO getTag(int tagId) {
        return null;
    }

    @Override
    public List<TagDTO> getTagsByCertificateId(int certificateId) {
        List<Tag> tags = tagDAO.findTagByCertificateId(certificateId);
        return tags.stream().map(tag -> tagDtoMapper.changeTagToTagDTO(tag)).collect(Collectors.toList());

    }

    @Nullable
    private TagDTO tagExist(TagDTO tagDTO) {
        Tag tag = tagDAO.findTagByName(tagDtoMapper.changeTagDtoToTag(tagDTO).getName());
        if (tag != null) {
            return tagDtoMapper.changeTagToTagDTO(tag);
        }
        return null;

    }
}
