package com.epam.esm.service;

import com.epam.esm.dto.TagDTO;

import java.util.List;

public interface TagService {
    List<TagDTO> findAll();

    void delete(long id);

    TagDTO create(TagDTO tagDTO);

    TagDTO find(String name);
}
