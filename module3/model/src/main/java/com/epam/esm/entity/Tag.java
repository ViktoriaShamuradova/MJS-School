package com.epam.esm.entity;

import com.epam.esm.listener.GeneralEntityListener;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@javax.persistence.Entity
@Table(name = "tags")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = {"certificates"})
@Data
@EntityListeners(GeneralEntityListener.class)
public class Tag extends com.epam.esm.entity.Entity<Long> {

    @Column(name = "name", unique = true)
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
