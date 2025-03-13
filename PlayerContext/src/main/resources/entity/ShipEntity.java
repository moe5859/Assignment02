package com.ship.shipcontext.shipcontext.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.persistence.Entity;

@Entity
public class ShipEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int x;
    private int y;

    private int size;
    private String player;


    public ShipEntity(int x, int y, int size, String player) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.player = player;
    }

    public ShipEntity() {

    }
    public Long getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setPlayer(String player) {
        this.player = player;
    }
}
