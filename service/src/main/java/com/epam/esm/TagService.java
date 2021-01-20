package com.epam.esm;

import com.epam.esm.dto.TagDTO;

import java.util.List;

public interface TagService {
    List<TagDTO> getAllTags();

    void deleteTag(int tagId);

    TagDTO createTag(TagDTO tagDTO);

    TagDTO getTag(int tagId);

    List<TagDTO> getTagsByCertificateId(int certificateId);

}
