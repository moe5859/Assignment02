package com.microservices.assignment02.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class PlayerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "game_id")
    @JsonBackReference
    private GameEntity game;

    public PlayerEntity() {
    }

    @Getter
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ShipEntity> ships = new ArrayList<>();

    public PlayerEntity(Long id, String name, GameEntity game) {
        this.id = id;
        this.name = name;
        this.game = game;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GameEntity getGame() {
        return game;
    }

    public void setGame(GameEntity game) {
        this.game = game;
    }
}
