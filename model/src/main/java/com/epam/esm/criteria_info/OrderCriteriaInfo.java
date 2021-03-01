package com.epam.esm.criteria_info;

import java.math.BigDecimal;
import java.util.Objects;

public class OrderCriteriaInfo extends CriteriaInfo {
    private Long idUser;
    private BigDecimal totalSum;

    public OrderCriteriaInfo(){}

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public BigDecimal getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(BigDecimal totalSum) {
        this.totalSum = totalSum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OrderCriteriaInfo that = (OrderCriteriaInfo) o;
        return idUser == that.idUser && Objects.equals(totalSum, that.totalSum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idUser, totalSum);
    }
}
