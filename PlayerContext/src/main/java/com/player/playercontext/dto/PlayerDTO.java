package com.player.playercontext.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PlayerDTO {
    private String name;
    @Getter
    private Long gameId;

    public PlayerDTO(String name, Long gameId) {
        this.name = name;
        this.gameId = gameId;
    }

}
