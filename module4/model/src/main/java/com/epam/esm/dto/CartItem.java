package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class CartItem {
    @Positive
    @Min(1)
    private long idCertificate;
    @Min(value = 1, message = "Enter count of certificates")
    @Max(value = 10, message = "Exceeding the maximum quantity 10")
    private int count;
}
