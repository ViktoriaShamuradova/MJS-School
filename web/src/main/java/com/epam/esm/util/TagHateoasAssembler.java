package com.epam.esm.util;

import com.epam.esm.criteria_info.PageInfo;
import com.epam.esm.criteria_info.TagCriteriaInfo;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.web.controller.TagController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * a class which assemble links for object TagDto
 */
@Component
public class TagHateoasAssembler {

    public void appendSelfReference(TagDTO dto) {
        dto.add(linkTo(methodOn(TagController.class).find(new PageInfo(), new TagCriteriaInfo())).withSelfRel());
        dto.add(linkTo(methodOn(TagController.class).delete(dto.getName())).withRel("DELETE: delete a current tag"));
    }

    public CollectionModel<TagDTO> toHateoasCollectionOfEntities(List<TagDTO> tags) {
        tags.forEach(this::appendSelfReference);
        Link selfLink = linkTo(TagController.class).withSelfRel();
        CollectionModel<TagDTO> collectionModel = CollectionModel.of(tags, selfLink);
        appendGenericOrderHateoasActions(collectionModel);
        return collectionModel;
    }

    private void appendGenericOrderHateoasActions(RepresentationModel dto) {
        dto.add(linkTo(TagController.class).withRel("GET: get all tags"));
        dto.add(linkTo(TagController.class).withRel("POST: create tag"));
        dto.add(linkTo(methodOn(TagController.class).findMostUsedTag()).withRel("GET: find most used tag"));
    }

    public void appendAsForMainEntity(TagDTO tag) {
        appendSelfReference(tag);
        appendGenericOrderHateoasActions(tag);
    }
}
