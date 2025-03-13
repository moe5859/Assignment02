package com.microservices.assignment02.service;

import com.microservices.assignment02.entity.PlayerEntity;
import com.microservices.assignment02.entity.ShipEntity;
import com.microservices.assignment02.repository.PlayerRepository;
import com.microservices.assignment02.repository.ShipRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipService {
    private final PlayerRepository playerRepository;
    private final ShipRepository shipRepository;

    public ShipService(PlayerRepository playerRepository, ShipRepository shipRepository) {
        this.playerRepository = playerRepository;
        this.shipRepository = shipRepository;
    }

    public List<ShipEntity> placeShips(Long playerId, List<String> positions) {
        PlayerEntity player = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found"));

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
