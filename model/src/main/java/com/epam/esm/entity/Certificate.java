package com.epam.esm.entity;

import java.time.LocalDateTime;

public class Certificate {

    private Integer id;
    private String name;
    private Integer price;
    private String description;
    private Integer duration;
    private LocalDateTime createDate;
    private LocalDateTime updateLastDate;


    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getUpdateLastDate() {
        return updateLastDate;
    }

    public void setUpdateLastDate(LocalDateTime updateLastDate) {
        this.updateLastDate = updateLastDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
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


}
