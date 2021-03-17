package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.time.Instant;

@NoArgsConstructor
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends RepresentationModel<UserDTO> {
    @Positive
    @Min(1)
    private Long id;
    @NotBlank(message = "name must not be blank")
    @Pattern(regexp = "[A-Za-zА-Яа-яЁё \\-]{1,45}", message = "name should contain from 1 to 45 characters")
    private String name;
    @NotBlank(message = "name must not be blank")
    @Pattern(regexp = "[A-Za-zА-Яа-яЁё \\-]{1,45}", message = "surname should contain from 1 to 45 characters")
    private String surname;
    @Null(message = "user create date cannot be defined by users")
    private Instant createDate;
    @Null(message = "user update date cannot be defined by users")
    private Instant lastUpdateDate;
}
