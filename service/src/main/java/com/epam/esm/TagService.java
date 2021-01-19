package com.epam.esm;

import com.epam.esm.dto.TagDTO;

import java.util.List;

public interface TagService {
    public List<TagDTO> getAllTags();

    public void deleteTag(int tagId);

    public TagDTO createTag(TagDTO tagDTO);

    public TagDTO getTag(int tagId);

    public List<TagDTO> getTagsByCertificateId(int certificateId);

}
