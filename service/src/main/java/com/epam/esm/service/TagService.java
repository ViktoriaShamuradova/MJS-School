package com.epam.esm.service;

import com.epam.esm.dto.TagDTO;

import java.util.List;

public interface TagService {
    List<TagDTO> findAll();

    void delete(long id);

    void create(TagDTO tagDTO);

    TagDTO find(long id);

    List<TagDTO> findByCertificateId(long certificateId);

    TagDTO find(String name);
}
