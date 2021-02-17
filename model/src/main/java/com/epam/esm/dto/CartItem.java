package com.epam.esm.dto;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.util.Objects;


public class CartItem {

    private CertificateDTO certificateDTO;
    @DecimalMin(value = "1", message = "Enter count of certificates")
    @DecimalMax(value = "10", message = "Exceeding the maximum quantity 10")
    private int count;

    public CartItem(CertificateDTO certificateDTO, int count) {
        this.certificateDTO = certificateDTO;
        this.count = count;
    }

    public CartItem() {
    }

    public CertificateDTO getCertificateDTO() {
        return certificateDTO;
    }

    public void setCertificateDTO(CertificateDTO certificateDTO) {
        this.certificateDTO = certificateDTO;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return count == cartItem.count && Objects.equals(certificateDTO, cartItem.certificateDTO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(certificateDTO, count);
    }
}
