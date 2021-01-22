package com.epam.esm.persistence;

import com.epam.esm.entity.Tag;

import java.util.List;

public interface TagDAO {
    List<Tag> findAllTagList();

    Tag findTagById(long id);

    int addNewTag(Tag tag);

    void deleteTag(long id);

    List<Tag> findTagByCertificateId(long certificateId);

    Tag findTagByName(String name);
}
