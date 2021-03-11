package com.epam.esm.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@javax.persistence.Entity
@Table(name = "tags")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = {"certificates"})
@Getter
@Setter
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

    public Tag(Long id, String name) {
        super(id);
        this.name = name;
    }

}
