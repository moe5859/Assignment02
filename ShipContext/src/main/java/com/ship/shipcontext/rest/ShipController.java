package com.ship.shipcontext.rest;


import com.ship.shipcontext.dto.ShipDTO;
import com.ship.shipcontext.entity.ShipEntity;
import com.ship.shipcontext.repository.ShipRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class ShipController {
    private final ShipRepository shipRepository;

    public ShipController(ShipRepository shipRepository) {
        this.shipRepository = shipRepository;
    }

    @PostMapping("set-ships")
    public ResponseEntity<String> placeShips(@RequestBody ShipDTO request) {
        Long playerId = request.getPlayerId(); // Player als Variable

        request.getPositions().forEach(position -> {
            int x = Character.getNumericValue(position.charAt(0));
            int y = Character.getNumericValue(position.charAt(1));

            ShipEntity ship = new ShipEntity();
            ship.setX(x);
            ship.setY(y);
            ship.setSize(1);
            ship.setPlayer("playerId"); // PlayerId statt PlayerEntity

            shipRepository.save(ship);
        });

        return ResponseEntity.ok("Ships have been set.");
    }

    @PostMapping("shoot")
    public ResponseEntity<String> shoot(
            @RequestParam Long playerId,
            @RequestParam Long gameId,
            @RequestParam int x,
            @RequestParam int y) {

        // Spieler als Variable
        Long attackerId = playerId;
        Long gameSessionId = gameId;

        // Gegner bestimmen (z.B. durch eine API oder eine externe Logik)
        Long opponentId = determineOpponentId(attackerId, gameSessionId);

        // Kontrolle, ob Schuss ein Treffer ist
        List<ShipEntity> enemyShips = shipRepository.findByPlayerId(opponentId);
        Optional<ShipEntity> hitShip = enemyShips.stream()
                .filter(ship -> ship.getX() == x && ship.getY() == y)
                .findFirst();

        if (hitShip.isPresent()) {
            shipRepository.delete(hitShip.get());

            boolean enemyStillHasShips = !shipRepository.findByPlayerId(opponentId).isEmpty();

            if (!enemyStillHasShips) {
                return ResponseEntity.ok("DIRECT HIT! Opponent has no ships left! Player " + attackerId + " is the victor!");
            }

            return ResponseEntity.ok("Target hit!");
        }

        return ResponseEntity.ok("Shot missed!");
    }

    @GetMapping("{playerId}")
    public List<ShipEntity> getShipsByPlayer(@PathVariable Long playerId) {
        return shipRepository.findByPlayerId(playerId);
    }

    // Dummy-Methode zur Gegnerbestimmung
    private Long determineOpponentId(Long playerId, Long gameId) {
        return (playerId % 2 == 0) ? playerId - 1 : playerId + 1; // Beispielhafte Gegnerzuweisung
    }
}