package com.epam.esm.criteria_info;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * Class containing information about certificate from url to build desired query
 */

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class CertificateCriteriaInfo extends CriteriaInfo {

    //private String orderBy;

    private List <@Pattern(regexp = "[:\\-0-9A-Za-zА-Яа-яЁё ]{1,256}", message = "tag name must contain " +
            "from 1 to 256 characters without punctuation marks")String> tagNames;

    @Pattern(regexp = "[-,.:!?0-9A-Za-zА-Яа-яЁё]{1,100}", message = "part of description or name must contain " +
            "from 1 to 100 " +
            "characters with punctuation marks")
    private String partOfNameOrDescription;

    @Pattern(regexp = "[0-9A-Za-zА-Яа-яЁё ]{1,45}", message = "certificate name must contain from 1 to 45 " +
            "characters without punctuation marks")
    private String name;
}
