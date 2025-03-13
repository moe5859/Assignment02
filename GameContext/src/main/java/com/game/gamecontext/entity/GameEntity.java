package com.game.gamecontext.entity;
import jakarta.persistence.*;

import jakarta.persistence.Entity;

@Entity
public class GameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private boolean active;

    private boolean finished = false;

    public GameEntity() {
        this.active = true;
    }

    public GameEntity(String name) {
        this.name = name;
        this.active = true;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isFinished() {
        return finished;
    }
}
