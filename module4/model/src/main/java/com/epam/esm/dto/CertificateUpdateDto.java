package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapitools.jackson.nullable.JsonNullable;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuppressWarnings("FieldMayBeFinal")
public class CertificateUpdateDto {

    @NotNull
    @Positive
    @Min(1)
    private JsonNullable<Long> id = JsonNullable.undefined();

    @NotNull
    @Pattern(regexp = "[-, .:!?0-9A-Za-zА-Яа-яЁё]{1,100}", message = "part of description or name must contain " +
            "from 1 to 100 " +
            "characters with punctuation marks")
    private JsonNullable<String> name = JsonNullable.undefined();

    @Pattern(regexp = "[-, .:!?0-9A-Za-zА-Яа-яЁё]{1,100}", message = "description must contain " +
            "from 1 to 100 " +
            "characters with punctuation marks")
    private JsonNullable<String> description = JsonNullable.undefined();

    @NotNull
    @Min(1)
    @Max(100)
    private JsonNullable<Integer> duration = JsonNullable.undefined();

    @NotNull
    @Digits(integer = 15, fraction = 2)
    @DecimalMin(value = "0", message = "Enter certificate price")
    private JsonNullable<BigDecimal> price = JsonNullable.undefined();

    private JsonNullable<Set<@Pattern(regexp = "[:\\-0-9A-Za-zА-Яа-яЁё ]{1,256}", message = "tag name must contain " +
            "from 1 to 256 characters without punctuation marks")TagDTO>> tags = JsonNullable.undefined();
}
