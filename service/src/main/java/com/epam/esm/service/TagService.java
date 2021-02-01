package com.epam.esm.service;

import com.epam.esm.entity.Tag;

import java.util.List;

public interface TagService extends CrudService<Tag, Long> {

    List<Tag> findByCertificateId(long id);

    Tag find(String name);

    void delete(String name);

    boolean isExist(Tag tag);
}
