package com.epam.esm.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public class Certificate {

    private long id;
    private String name;
    private BigDecimal price;
    private String description;
    private Integer duration;
    private Instant createDate;
    private Instant updateLastDate;

    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Instant getUpdateLastDate() {
        return updateLastDate;
    }
    public void setUpdateLastDate(Instant updateLastDate) {
        this.updateLastDate = updateLastDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
//переопределить
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Certificate that = (Certificate) o;
        if(!(that.getPrice().compareTo(this.getPrice())==0)) return false;

        return id == that.id && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(duration, that.duration) && Objects.equals(createDate, that.createDate) && Objects.equals(updateLastDate, that.updateLastDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, description, duration, createDate, updateLastDate);
    }
}
