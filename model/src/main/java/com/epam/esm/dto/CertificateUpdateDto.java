package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapitools.jackson.nullable.JsonNullable;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuppressWarnings("FieldMayBeFinal")
public class CertificateUpdateDto {

    @NotNull
    private JsonNullable<Long> id = JsonNullable.undefined();

    @NotNull
    private JsonNullable<String> name = JsonNullable.undefined();

    private JsonNullable<String> description = JsonNullable.undefined();

    @NotNull
    private JsonNullable<Integer> duration = JsonNullable.undefined();

    @NotNull
    private JsonNullable<BigDecimal> price = JsonNullable.undefined();

    private JsonNullable<Set<TagDTO>> tags = JsonNullable.undefined();
}
