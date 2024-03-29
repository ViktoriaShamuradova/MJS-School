package com.epam.esm.entity;

import com.epam.esm.listener.GeneralEntityListener;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

@javax.persistence.Entity
@Table(name = "certificates")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = {"tags"})
@EntityListeners(GeneralEntityListener.class)
public class Certificate extends com.epam.esm.entity.Entity<Long> {

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

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "certificates_tags",
            joinColumns = @JoinColumn(name = "id_certificate"),
            inverseJoinColumns = @JoinColumn(name = "id_tag"))
    private Set<Tag> tags;

    public Certificate(long id) {
        super(id);
    }
}
