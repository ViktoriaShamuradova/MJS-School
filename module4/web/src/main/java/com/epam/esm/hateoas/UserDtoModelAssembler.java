package com.epam.esm.hateoas;

import com.epam.esm.controller.UserController;
import com.epam.esm.dto.UserDTO;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserDtoModelAssembler extends RepresentationModelAssemblerSupport<UserDTO, UserDTO> {

    public UserDtoModelAssembler() {
        super(UserController.class, UserDTO.class);
    }

    @Override
    public UserDTO toModel(UserDTO dto) {
        appendSelfReference(dto);
        return dto;
    }

    private void appendSelfReference(UserDTO dto) {
        dto.add(linkTo(methodOn(UserController.class)
                .find(dto.getId()))
                .withRel("find by id")
                .withType(HttpMethod.GET.name()));
    }
}
