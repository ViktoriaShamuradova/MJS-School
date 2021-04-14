package com.epam.esm.dto;

import com.epam.esm.Role;
import com.epam.esm.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.time.Instant;

@NoArgsConstructor
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends RepresentationModel<UserDTO> {

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

    @NotBlank(message = "password must not be empty")
    @Pattern(regexp = "[A-Za-zА-Яа-яЁё0-9!?@#$%^&*()\\-_+:;,.]{6,256}", message = "password can contain latin and " +
            "cyrillic characters, punctuation marks and special characters '@#$%^&*()'. Length must be between " +
            "6 and 256 characters")
    private String password;

    @NotNull(message = "username must be filled")
    @Email(message = "username should be valid. Example, xxxxxxx@xxxxx.domain")
    private String username;
    @Null
    private Role role;
    @Null
    private Status status;
}
