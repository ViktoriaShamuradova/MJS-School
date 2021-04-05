package com.epam.esm.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class RegistrationUserDto {

    @NotBlank(message = "name must not be blank")
    @Pattern(regexp = "[A-Za-zА-Яа-яЁё \\-]{1,45}", message = "name should contain from 1 to 45 characters")
    private String name;
    @NotBlank(message = "name must not be blank")
    @Pattern(regexp = "[A-Za-zА-Яа-яЁё \\-]{1,45}", message = "surname should contain from 1 to 45 characters")
    private String surname;
    @NotNull(message = "email must be filled")
    @Email(message = "email should be valid. Example, xxxxxxx@xxxxx.domain")
    private String email;
    @NotBlank(message = "password must not be empty")
    @Pattern(regexp = "[A-Za-zА-Яа-яЁё0-9!?@#$%^&*()\\-_+:;,.]{6,256}", message = "password can contain latin and " +
            "cyrillic characters, punctuation marks and special characters '@#$%^&*()'. Length must be between " +
            "6 and 256 characters")
    private String password;
}
