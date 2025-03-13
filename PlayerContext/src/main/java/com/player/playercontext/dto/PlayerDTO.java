package com.player.playercontext.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PlayerDTO {
    private String name;
    private Long gameId;

    public PlayerDTO(String name, Long gameId) {
        this.name = name;
        this.gameId = gameId;
    }

    public Long getGameId() {
        return gameId;
    }
}
