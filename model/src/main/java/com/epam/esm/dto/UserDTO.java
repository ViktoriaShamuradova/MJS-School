package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.Instant;

@NoArgsConstructor
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends RepresentationModel<UserDTO> {
    private Long id;
    private String name;
    private String surname;
    private Instant createDate;
    private Instant lastUpdateDate;
}
