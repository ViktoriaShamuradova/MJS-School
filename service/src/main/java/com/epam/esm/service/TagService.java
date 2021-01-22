package com.epam.esm.service;

import com.epam.esm.dto.TagDTO;

import java.util.List;

public interface TagService {
    List<TagDTO> getAllTags();

    void deleteTag(long tagId);

    TagDTO createTag(TagDTO tagDTO);

    TagDTO getTag(long tagId);

    List<TagDTO> getTagsByCertificateId(long certificateId);

    TagDTO getTag(String name);
}
