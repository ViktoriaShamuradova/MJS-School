package com.epam.esm.dto;

import org.openapitools.jackson.nullable.JsonNullable;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

@SuppressWarnings("FieldMayBeFinal")
public class CertificateUpdateDto {

    @NotNull
    private JsonNullable<String> name = JsonNullable.undefined();

    private JsonNullable<String> description = JsonNullable.undefined();

    @NotNull
    private JsonNullable<Integer> duration = JsonNullable.undefined();

    @NotNull
    private JsonNullable<BigDecimal> price = JsonNullable.undefined();

    private JsonNullable<Set<TagDTO>> tags = JsonNullable.undefined();

    public JsonNullable<String> getName() {
        return name;
    }


    public JsonNullable<String> getDescription() {
        return description;
    }

    public JsonNullable<Integer> getDuration() {
        return duration;
    }

    public JsonNullable<Set<TagDTO>> getTags() {
        return tags;
    }

    public JsonNullable<BigDecimal> getPrice() {
        return price;
    }

}
