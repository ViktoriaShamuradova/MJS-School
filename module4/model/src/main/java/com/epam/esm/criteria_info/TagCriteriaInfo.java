package com.epam.esm.criteria_info;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class TagCriteriaInfo extends CriteriaInfo {

    @Pattern(regexp = "[0-9A-Za-zА-Яа-яЁё ]{1,45}", message = "tag name must contain from 1 to 45 " +
            "characters without punctuation marks")
    private String name;
}
