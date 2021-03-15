package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true, exclude = {"tags"})
public class CertificateDTO extends RepresentationModel<CertificateDTO> {

    private long id;

    @Size(min = 3, max = 20, message = "The certificate name could be between 3 and 20 symbols")
    private String name;

    @Size(min = 5, max = 100, message = "Description should be between 5 and 100 symbols")
    private String description;

    @Digits(integer = 15, fraction = 2)
    @DecimalMin(value = "0", message = "Enter certificate price")
    private BigDecimal price;

    @DecimalMin(value = "1", message = "Enter certificate duration more than 1 day")
    private Integer duration;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "UTC")
    private Instant createDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "UTC")
    private Instant updateLastDate;

    private Set<TagDTO> tags = new HashSet<>();

    @JsonCreator
    public CertificateDTO() {
    }
}
