package com.microservices.assignment02.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Entity;

@Entity
public class GameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "current_turn_player_id")
    @JsonIgnore
    private PlayerEntity currentTurn;

    @ManyToOne
    @JoinColumn(name = "winner_id")
    @JsonIgnore
    private PlayerEntity winner;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<PlayerEntity> players = new ArrayList<>();

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

    public List<PlayerEntity> getPlayers() {
        return players;
    }

    public PlayerEntity getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(PlayerEntity currentTurn) {
        this.currentTurn = currentTurn;
    }
    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public PlayerEntity getWinner() {
        return winner;
    }

    public void setWinner(PlayerEntity winner) {
        this.winner = winner;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
