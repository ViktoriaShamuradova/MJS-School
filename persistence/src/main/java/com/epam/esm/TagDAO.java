package com.epam.esm;

import com.epam.esm.entity.Tag;

import java.util.List;

public interface TagDAO {
    List<Tag> findAllTagList();

    Tag findTagById(int id);

    int addNewTag(Tag tag);

    void deleteTag(int id);

    List<Tag> findTagByCertificateId(int certificateId);

    Tag findTagByName(String name);
}
