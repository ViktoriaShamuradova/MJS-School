package com.epam.esm.persistence;

import com.epam.esm.dto.Person;
import com.epam.esm.entity.Certificate;

import java.util.List;

public interface CertificateDAO extends CrudDAO<Certificate, Long> {

    void addLinkCertificateWithTags(long certificateId, long tagId);

    List<Certificate> findByTagId(long id);

    List<Certificate> findByPartOfNameOrDescription(String partOfNameOrDescription);

    void deleteAllLinksWithTags(long id);

    List<Certificate> findByTagNames(List<String> tagNames);

    long getCount();

    Person createPerson(Person person);

    List<Person> findAllPersons();
}
