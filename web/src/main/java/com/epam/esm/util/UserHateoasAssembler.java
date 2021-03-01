package com.epam.esm.util;

import com.epam.esm.dto.UserDTO;
import com.epam.esm.web.controller.UserController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserHateoasAssembler {

    public void appendSelfReference(UserDTO dto) {
        dto.add(linkTo(methodOn(UserController.class).find(dto.getId())).withSelfRel());
    }

    public CollectionModel<UserDTO> toHateoasCollectionOfEntities(List<UserDTO> users) {
        users.forEach(this::appendSelfReference);
        Link selfLink = linkTo(UserController.class).withSelfRel();
        CollectionModel<UserDTO> collectionModel = CollectionModel.of(users, selfLink);
        appendGenericOrderHateoasActions(collectionModel);
        return collectionModel;
    }

    private void appendGenericOrderHateoasActions(RepresentationModel dto) {
        dto.add(linkTo(UserController.class).withRel("GET: get all users"));
    }

    public void appendAsForMainEntity(UserDTO userDTO) {
        appendSelfReference(userDTO);
        appendGenericOrderHateoasActions(userDTO);
    }
}
