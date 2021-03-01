package com.epam.esm.entity;

import javax.annotation.sql.DataSourceDefinition;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "certificates")
public class Certificate {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "description")
    private String description;
    @Column(name = "duration")
    private Integer duration;
    @Column(name = "create_date", columnDefinition = "TIMESTAMP")
    private Instant createDate;
    @Column(name = "last_update_date", columnDefinition = "TIMESTAMP")
    private Instant updateLastDate;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE,CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "certificates_tags",
            joinColumns = @JoinColumn(name = "id_certificate"),
            inverseJoinColumns = @JoinColumn(name = "id_tag"))
    private Set<Tag> tags;

    public Certificate(long id) {
        this.id = id;
    }
    public Certificate(){}

    public Instant getCreateDate() {
        return createDate;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
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

    public void setId(long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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
