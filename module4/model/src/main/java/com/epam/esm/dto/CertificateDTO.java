package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true, exclude = {"tags"})
public class CertificateDTO extends RepresentationModel<CertificateDTO> {

    private long id;

    @Pattern(regexp = "[0-9A-Za-zА-Яа-яЁё ]{1,45}", message = "certificate name must contain from 1 to 45 " +
            "characters without punctuation marks")
    private String name;

    @Pattern(regexp = "[-, .:!?0-9A-Za-zА-Яа-яЁё]{1,100}", message = "description must contain " +
            "from 1 to 100 " +
            "characters with punctuation marks")
    private String description;

    @Digits(integer = 15, fraction = 2)
    @DecimalMin(value = "0", message = "Enter certificate price")
    private BigDecimal price;

    @DecimalMin(value = "1", message = "Enter certificate duration more than 1 day")
    private Integer duration;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "UTC")
    @Null(message = "certificate create date cannot be defined by users")
    private Instant createDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "UTC")
    @Null(message = "certificate update date cannot be defined by users")
    private Instant updateLastDate;

    private Set<TagDTO> tags = new HashSet<>();

    @JsonCreator
    public CertificateDTO() {
    }
}
