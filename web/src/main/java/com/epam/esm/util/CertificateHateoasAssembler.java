package com.epam.esm.util;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.web.controller.CertificateController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * a class which assemble links for object CertificateDto
 */
@Component
public class CertificateHateoasAssembler {

    private final TagHateoasAssembler tagAssembler;

    @Autowired
    public CertificateHateoasAssembler(TagHateoasAssembler tagAssembler) {
        this.tagAssembler = tagAssembler;
    }

    private void appendSelfReference(CertificateDTO dto) {
        dto.add(linkTo(methodOn(CertificateController.class).find(dto.getId())).withSelfRel());
        dto.add(linkTo(CertificateController.class).withRel("PATCH: update a current certificate"));
        dto.add(linkTo(CertificateController.class).withRel("DELETE: delete a current certificate"));

        dto.getTags().forEach(tagAssembler::appendSelfReference);
    }

    public CollectionModel<CertificateDTO> toHateoasCollectionOfEntities(List<CertificateDTO> certificates) {
        certificates.forEach(this::appendSelfReference);
        Link selfLink = linkTo(CertificateController.class).withSelfRel();
        CollectionModel<CertificateDTO> collectionModel = CollectionModel.of(certificates, selfLink);
        appendGenericCertificateHateoasActions(collectionModel);
        return collectionModel;
    }

    public void appendGenericCertificateHateoasActions(RepresentationModel dto) {
        dto.add(linkTo(CertificateController.class).withRel("GET: get all certificates"));
        dto.add(linkTo(CertificateController.class).withRel("POST: create certificate"));
    }

    public void appendAsForMainEntity(CertificateDTO certificate) {
        appendSelfReference(certificate);
        appendGenericCertificateHateoasActions(certificate);
    }
}
