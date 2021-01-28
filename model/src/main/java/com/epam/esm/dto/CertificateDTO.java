package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

public class CertificateDTO {

    private long id;

    @Size(min = 3, max = 20, message = "The certificate name could be between 3 and 20 symbols")
    private String name;

    @Size(min = 5, max = 100, message = "Description should be between 5 and 100 symbols")
    private String description;

    @Digits(integer = 15, fraction = 2)
    @DecimalMin(value = "0", message = "Enter certificate price")
    private BigDecimal price;

    @DecimalMin(value = "1", message = "Enter certificate duration more than 1 day")
    private int duration;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Instant createDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Instant updateLastDate;

    private List<TagDTO> tagList;

    public CertificateDTO() {
    }

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

    public long getId() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<TagDTO> getTagList() {
        return tagList;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setTagList(List<TagDTO> tagList) {
        this.tagList = tagList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CertificateDTO that = (CertificateDTO) o;
        return id == that.id && duration == that.duration && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(price, that.price) && Objects.equals(createDate, that.createDate) && Objects.equals(updateLastDate, that.updateLastDate) && Objects.equals(tagList, that.tagList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, duration, createDate, updateLastDate, tagList);
    }

    @Override
    public String toString() {
        return "CertificateDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", createDate=" + createDate +
                ", updateLastDate=" + updateLastDate +
                ", tagList=" + tagList +
                '}';
    }
}
