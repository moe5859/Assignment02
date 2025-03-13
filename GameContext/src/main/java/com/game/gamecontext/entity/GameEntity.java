package com.game.gamecontext.entity;
import jakarta.persistence.*;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}
