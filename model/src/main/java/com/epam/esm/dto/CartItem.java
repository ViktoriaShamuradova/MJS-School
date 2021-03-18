package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class CartItem {

    private long idCertificate;
    @DecimalMin(value = "1", message = "Enter count of certificates")
    @DecimalMax(value = "10", message = "Exceeding the maximum quantity 10")
    private int count;
    private BigDecimal priceOfCertificate;
}
