package com.epam.esm.entity;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@javax.persistence.Entity
@Table(name = "tags")
public class Tag extends com.epam.esm.entity.Entity<Long>{

    @Column(name = "name", unique=true)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "certificates_tags",
            joinColumns = @JoinColumn(name = "id_tag"),
            inverseJoinColumns = @JoinColumn(name = "id_certificate"))
    private Set<Certificate> certificates;

    public Tag(String name) {
        super();
        this.name = name;
    }

    public Tag() {
        super();
    }

    public Tag(Long id, String name) {
        super(id);
        this.name = name;
    }

    public Set<Certificate> getCertificates() {
        return certificates;
    }

    public void setCertificates(Set<Certificate> certificates) {
        this.certificates = certificates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(name, tag.name) && Objects.equals(certificates, tag.certificates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
