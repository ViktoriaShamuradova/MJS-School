package com.epam.esm.dto;

import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

public class TagDTO extends RepresentationModel<TagDTO> {

    private String name;

    public TagDTO(){}
    public TagDTO(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TagDTO tagDTO = (TagDTO) o;
        return Objects.equals(name, tagDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }
}
