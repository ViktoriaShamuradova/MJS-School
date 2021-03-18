package com.epam.esm.listener;

import com.epam.esm.entity.Entity;

import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

public class GeneralEntityListener {

    @PreUpdate
    void preUpdate(Entity<Long> entity) {
        System.out.println("Attempting to update an entity" + entity.getClass().getName() + " with ID: " + entity.getId());
    }

    @PostUpdate
    void postUpdate(Entity<Long> entity) {
        System.out.println("Updated an entity: " + entity.getClass().getSimpleName());
    }

    @PreRemove
    void preRemove(Entity<Long> entity) {
        System.out.println("Attempting to delete an entity" + entity.getClass().getSimpleName() + "  with ID: " + entity.getId());
    }

    @PostRemove
    void postRemove(Entity<Long> entity) {
        System.out.println("Deleted an entity: " + entity.getClass().getSimpleName());
    }
}