package com.epam.esm.persistence;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDAO {
    List<Tag> findAll();

    Optional<Tag> find(long id);

    void add(TagDTO tag);

    void delete(long id);

    List<Tag> findByCertificateId(long certificateId);

    Optional<Tag> find(String name);
}
