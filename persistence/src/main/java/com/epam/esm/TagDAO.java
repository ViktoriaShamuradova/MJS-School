package com.epam.esm;

import com.epam.esm.entity.Tag;
import java.util.List;

public interface TagDAO {
    public List<Tag> findAllTagList();

    public Tag findTagById(int id);

    public int addNewTag(Tag tag);

    public void deleteTag(int id);

    public List<Tag> findTagByCertificateId(int certificateId);
}
