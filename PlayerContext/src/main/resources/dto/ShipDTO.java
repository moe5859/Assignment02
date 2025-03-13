package com.ship.shipcontext.shipcontext.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ShipDTO {
    private Long playerId;
    private List<String> positions;

    public ShipDTO(Long playerId, List<String> positions) {
        this.playerId = playerId;
        this.positions = positions;
    }
}
