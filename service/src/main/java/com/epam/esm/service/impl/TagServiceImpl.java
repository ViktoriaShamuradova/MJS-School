package com.epam.esm.service.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.persistence.TagDAO;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.NoSuchResourceException;
import com.epam.esm.service.exception.TagAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    private final TagDAO tagDAO;

    @Autowired
    public TagServiceImpl(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tag> findAll() {
        return tagDAO.findAll();
    }

    @Override
    public Tag create(Tag tag) {
        if (tagDAO.find(tag.getName()).isPresent())
            throw new TagAlreadyExistsException(".tag", "name= " + tag.getName());
        tagDAO.create(tag);
        return tag;
    }

    @Override
    @Transactional(readOnly = true)
    public Tag find(Long id) {
        return tagDAO.find(id).orElseThrow(() -> new NoSuchResourceException(".tag", "id= " + id));
    }

    @Override
    public void delete(Long id) {
        tagDAO.find(id).orElseThrow(() -> new NoSuchResourceException(".tag", "id= " + id));
        tagDAO.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tag> findByCertificateId(long id) {
        return tagDAO.findByCertificateId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Tag find(String name) {
        return tagDAO.find(name).orElseThrow(() -> new NoSuchResourceException(".tag", "name= " + name));
    }

    @Override
    public void delete(String name) {
        tagDAO.find(name).orElseThrow(() -> new NoSuchResourceException(".tag", "name= " + name));
        tagDAO.delete(name);
    }

    @Override
    public void update(Tag tag) {

    }
}
