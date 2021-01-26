package com.epam.esm.persistence;

import com.epam.esm.entity.Tag;

import java.util.List;

public interface TagDAO {
    List<Tag> findAll();

    Tag find(long id);

    int add(Tag tag);

    void delete(long id);

    List<Tag> findByCertificateId(long certificateId);

    Tag find(String name);
}
