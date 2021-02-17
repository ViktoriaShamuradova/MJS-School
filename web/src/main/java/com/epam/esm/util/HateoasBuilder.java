package com.epam.esm.util;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.User;
import com.epam.esm.web.controller.CertificateController;
import com.epam.esm.web.controller.TagController;
import com.epam.esm.web.controller.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.mediatype.hal.HalModelBuilder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class HateoasBuilder {

    private final PaginationPreparer paginationPreparer;

    @Autowired
    HateoasBuilder(PaginationPreparer paginationPreparer) {
        this.paginationPreparer = paginationPreparer;
    }

    public RepresentationModel<?> addLinksForListOfCertificates(List<CertificateDTO> certificates,
                                                                Map<String, String> params, long certificatesCount) {

        certificates.forEach(certificateDTO -> certificateDTO.add(linkTo(methodOn(CertificateController.class)
                .find(certificateDTO.getId()))
                .withSelfRel()));
        Map<String, Long> page = paginationPreparer.preparePage(params, certificatesCount);
        List<Link> links = paginationPreparer.preparePaginationLinks
                (methodOn(CertificateController.class).findAll(params), params);

        CollectionModel<CertificateDTO> collectionModel = CollectionModel.of(certificates);
        return buildModel(collectionModel, links, page);
    }

    private RepresentationModel<?> buildModel(Object entity, List<Link> links, Map<String, Long> page) {
        return HalModelBuilder.emptyHalModel().links(links).embed(page, LinkRelation.of(Constant.CURRENT_PAGE)).build();
    }

    public CertificateDTO addLinkForCertificate(CertificateDTO certificate) {
        certificate.getTags().forEach(tag -> tag.add(linkTo(methodOn(TagController.class)
                .find(tag.getName()))
                .withSelfRel()));
        return certificate;
    }

    public RepresentationModel<?> addLinksForListOfTag(List<TagDTO> tags, Map<String, String> params, long tagCount) {
        tags.forEach(tag -> tag.add(linkTo(methodOn(TagController.class)
                .find(tag.getName()))
                .withSelfRel()));
        Map<String, Long> page = paginationPreparer.preparePage(params, tagCount);
        List<Link> links = paginationPreparer.preparePaginationLinks(methodOn(TagController.class).findAll(params), params);
        CollectionModel<TagDTO> collectionModel = CollectionModel.of(tags);
        return buildModel(collectionModel, links, page);
    }

    public TagDTO addLinksForTag(TagDTO tag) {
        tag.add(linkTo(methodOn(TagController.class)
                .find(tag.getName()))
                .withSelfRel());
        tag.add(createLinkToGetCertificates(Constant.TAG,
                tag.getName(), Constant.CERTIFICATES));
        return tag;
    }

    private Link createLinkToGetCertificates(String param, String value, String rel) {
        Map<String, String> params = new HashMap<>();
        params.put(param, value);
        return linkTo(methodOn(CertificateController.class)
                .findAll(params))
                .withRel(rel);
    }

    public RepresentationModel<?> addLinksForListOfUsers(List<UserDTO> users, Map<String, String> params, long userCount) {
        users.forEach(user -> user.add(linkTo(methodOn(UserController.class)
                .find(user.getId()))
                .withSelfRel()));
        Map<String, Long> page = paginationPreparer.preparePage(params, userCount);
        List<Link> links = paginationPreparer.preparePaginationLinks(
                methodOn(UserController.class).findAll(params), params);
        CollectionModel<UserDTO> collectionModel = CollectionModel.of(users);
        return buildModel(collectionModel, links, page);
    }

    private static class Constant {
        private final static String CURRENT_PAGE = "current page";
        private final static String TAG = "tag";
        private final static String CERTIFICATES = "certificates";
//        private final static String USER_ID = "userId";
//        private final static String ORDERS = "orders";
//        private final static String USERS_ORDERS = "user's orders";
    }

}
