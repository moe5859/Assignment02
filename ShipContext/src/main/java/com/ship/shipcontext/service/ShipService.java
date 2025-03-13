package com.ship.shipcontext.service;

import com.ship.shipcontext.entity.ShipEntity;

import com.ship.shipcontext.repository.ShipRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipService {
    private final ShipRepository shipRepository;

    public ShipService( ShipRepository shipRepository) {
        this.shipRepository = shipRepository;
    }

    public List<ShipEntity> placeShips(Long playerId, List<String> positions) {
        String player = "player";

        for (String pos : positions) {
            int x = Character.getNumericValue(pos.charAt(0));
            int y = Character.getNumericValue(pos.charAt(1));

            ShipEntity ship = new ShipEntity();
            ship.setX(x);
            ship.setY(y);
            ship.setSize(1);
            ship.setPlayer(player);

            shipRepository.save(ship);
        }

        return shipRepository.findByPlayerId(playerId);
    }

    public boolean isHit(Long opponentId, int x, int y) {
        return shipRepository.findByPlayerId(opponentId).stream()
                .anyMatch(ship -> ship.getX() == x && ship.getY() == y);
    }

    public List<ShipEntity> getShipsByPlayer(Long playerId) {
        return shipRepository.findByPlayerId(playerId);
    }
}
