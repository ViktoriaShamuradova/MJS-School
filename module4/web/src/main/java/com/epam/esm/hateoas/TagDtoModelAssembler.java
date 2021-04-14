package com.epam.esm.hateoas;

import com.epam.esm.controller.TagController;
import com.epam.esm.criteria_info.TagCriteriaInfo;
import com.epam.esm.dto.TagDTO;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagDtoModelAssembler extends RepresentationModelAssemblerSupport<TagDTO, TagDTO> {

    public TagDtoModelAssembler() {
        super(TagController.class, TagDTO.class);
    }


    @Override
    public TagDTO toModel(TagDTO dto) {
        appendSelfReference(dto);
        return dto;
    }

    private void appendSelfReference(TagDTO dto) {
        dto.add(linkTo(methodOn(TagController.class)
                .find(null, new TagCriteriaInfo(dto.getName())))
                .withSelfRel()
                .withType(HttpMethod.GET.name()));
        dto.add(linkTo(methodOn(TagController.class)
                .delete(dto.getName()))
                .withRel("delete a current certificate")
                .withType(HttpMethod.DELETE.name()));
    }

    public void appendGenericTagHateoasActions(RepresentationModel dto) {
        dto.add(linkTo(TagController.class).withRel("GET: get all tags"));
        dto.add(linkTo(TagController.class).withRel("POST: create tag"));
        dto.add(linkTo(methodOn(TagController.class).findMostUsedTag()).withRel("GET: find most used tag"));
    }
}
