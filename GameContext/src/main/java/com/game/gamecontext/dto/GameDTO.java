package com.game.gamecontext.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GameDTO {
    private String name;

    public GameDTO(String name) {
        this.name = name;
    }

}
