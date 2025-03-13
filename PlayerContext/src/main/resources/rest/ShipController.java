package com.ship.shipcontext.shipcontext.rest;

import com.ship.shipcontext.dto.ShipDTO;
import com.ship.shipcontext.repository.ShipRepository;
import com.ship.shipcontext.entity.ShipEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class ShipController {
    private final ShipRepository shipRepository;

    public ShipController(ShipRepository shipRepository) {
        this.shipRepository = shipRepository;
    }

    @PostMapping("set-ships")
    public ResponseEntity<String> placeShips(@RequestBody ShipDTO request) {

            request.getPositions().forEach(position -> {
                int x = Character.getNumericValue(position.charAt(0));
                int y = Character.getNumericValue(position.charAt(1));

                ShipEntity ship = new ShipEntity();
                ship.setX(x);
                ship.setY(y);
                ship.setSize(1);

                shipRepository.save(ship);
            });

            return ResponseEntity.ok("Ships have been set.");
    }

    @GetMapping("{playerId}")
    public List<ShipEntity> getShipsByPlayer(@PathVariable Long playerId) {
        return shipRepository.findByPlayerId(playerId);
    }
}

