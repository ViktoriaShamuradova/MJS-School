package com.epam.esm.criteria_info;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class UserCriteriaInfo extends CriteriaInfo {

    @Pattern(regexp = "[A-Za-zА-Яа-яЁё \\-]{1,45}", message = "name should contain from 1 to 45 characters")
    private String name;

    @Pattern(regexp = "[A-Za-zА-Яа-яЁё \\-]{1,45}", message = "surname should contain from 1 to 45 characters")
    private String surname;
}

