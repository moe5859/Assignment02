package com.microservices.assignment02.rest;

import com.microservices.assignment02.dto.ShipDTO;
import com.microservices.assignment02.entity.GameEntity;
import com.microservices.assignment02.entity.PlayerEntity;
import com.microservices.assignment02.entity.ShipEntity;
import com.microservices.assignment02.repository.GameRepository;
import com.microservices.assignment02.repository.PlayerRepository;
import com.microservices.assignment02.repository.ShipRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class ShipController {
    private final ShipRepository shipRepository;
    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;

    public ShipController(ShipRepository shipRepository, PlayerRepository playerRepository, GameRepository gameRepository) {
        this.shipRepository = shipRepository;
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
    }

    @PostMapping("set-ships")
    public ResponseEntity<String> placeShips(@RequestBody ShipDTO request) {
            PlayerEntity player = playerRepository.findById(request.getPlayerId())
                    .orElseThrow(() -> new IllegalArgumentException("Player does not exist"));

            request.getPositions().forEach(position -> {
                int x = Character.getNumericValue(position.charAt(0));
                int y = Character.getNumericValue(position.charAt(1));

                ShipEntity ship = new ShipEntity();
                ship.setX(x);
                ship.setY(y);
                ship.setSize(1);
                ship.setPlayer(player);

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

        GameEntity game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid game ID"));

        PlayerEntity attacker = playerRepository.findById(playerId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid player ID"));

        if (!attacker.equals(game.getCurrentTurn())) {
            return ResponseEntity.badRequest().body("It's not your turn!");
        }

        // finde gegner
        PlayerEntity opponent = game.getPlayers().stream()
                .filter(p -> !p.getId().equals(playerId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Opponent not found"));

        // kontrolle ob schuss ein treffer ist
        List<ShipEntity> enemyShips = shipRepository.findByPlayerId(opponent.getId());
        Optional<ShipEntity> hitShip = enemyShips.stream()
                .filter(ship -> ship.getX() == x && ship.getY() == y)
                .findFirst();

        if (hitShip.isPresent()) {
            shipRepository.delete(hitShip.get());

            boolean enemyStillHasShips = !shipRepository.findByPlayerId(opponent.getId()).isEmpty();

            if (!enemyStillHasShips) {
                game.setFinished(true);
                game.setActive(false);
                game.setWinner(attacker);
                gameRepository.save(game);

                return ResponseEntity.ok("DIRECT HIT! " + opponent.getName() + " has no ships left! "
                        + attacker.getName() + " is the victor!");
            }

            game.setCurrentTurn(opponent);
            gameRepository.save(game);
            return ResponseEntity.ok("Target hit!");
        }

        game.setCurrentTurn(opponent);
        gameRepository.save(game);
        return ResponseEntity.ok("Shot missed!");
    }

    @GetMapping("{playerId}")
    public List<ShipEntity> getShipsByPlayer(@PathVariable Long playerId) {
        return shipRepository.findByPlayerId(playerId);
    }
}

