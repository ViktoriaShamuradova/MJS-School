package com.epam.esm.persistence;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDAO extends CrudDAO<Tag, Long> {
    List<Tag> findByCertificateId(long id);

    Optional<Tag> find(String name);

    void delete(String name);
}
