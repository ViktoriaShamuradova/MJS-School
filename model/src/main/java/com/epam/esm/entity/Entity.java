package com.epam.esm.entity;

import javax.persistence.*;

@MappedSuperclass
public abstract class Entity<ID> {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ID id;

    public Entity(ID id){
        this.id=id;
    }

    public Entity(){
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }
}
