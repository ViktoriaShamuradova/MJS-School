package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Pattern;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class TagDTO extends RepresentationModel<TagDTO> {

    @Pattern(regexp = "[0-9A-Za-zА-Яа-яЁё ]{1,45}", message = "tag name must contain from 1 to 45 " +
            "characters without punctuation marks")
    private String name;
}
