package com.epam.esm.util;

import com.epam.esm.criteria_info.PageInfo;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.web.controller.UserController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserHateoasAssembler {

    public void appendSelfReference(UserDTO dto) {
        dto.add(linkTo(methodOn(UserController.class).find(dto.getId())).withSelfRel());
    }

    public CollectionModel<UserDTO> toHateoasCollectionOfEntities(List<UserDTO> users, PageInfo pageInfo, long entityCount) {
        users.forEach(this::appendSelfReference);
        Link selfLink = linkTo(UserController.class).withSelfRel();
        CollectionModel<UserDTO> collectionModel = CollectionModel.of(users, selfLink);
        appendGenericUserHateoasActions(collectionModel);
        addPageNavigation(collectionModel, pageInfo, entityCount);

        return collectionModel;
    }

    private void appendGenericUserHateoasActions(RepresentationModel dto) {
        dto.add(linkTo(UserController.class).withRel("GET: get all users"));
    }

    private void addPageNavigation(RepresentationModel dto, PageInfo pageInfo, long entityCount) {
        PageNavigationLinkGenerator pageUrlGenerator= new PageNavigationLinkGenerator(pageInfo, entityCount, "/springboot-rest/users");
        Map<String, Link> links = pageUrlGenerator.getLinks();

        for(Map.Entry<String, Link> entry : links.entrySet()) {
            dto.add(entry.getValue().withRel(entry.getKey()));
        }
    }

    public void appendAsForMainEntity(UserDTO userDTO) {
        appendSelfReference(userDTO);
        appendGenericUserHateoasActions(userDTO);
    }
}
