package com.microservices.assignment02.rest;

import com.microservices.assignment02.dto.GameDTO;
import com.microservices.assignment02.entity.GameEntity;
import com.microservices.assignment02.entity.PlayerEntity;
import com.microservices.assignment02.repository.GameRepository;
import com.microservices.assignment02.repository.ShipRepository;
import com.microservices.assignment02.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping
public class GameController {
    private final GameService gameService;
    private final GameRepository gameRepository;
    private final ShipRepository shipRepository;

    public GameController(GameService gameService, GameRepository gameRepository, ShipRepository shipRepository) {
        this.gameService = gameService;
        this.gameRepository = gameRepository;
        this.shipRepository = shipRepository;
    }

    @PostMapping("createGame")
    public GameEntity createGame(@RequestBody GameDTO request) {
        return gameService.saveGame(new GameEntity(request.getName()));
    }

    @GetMapping("getGames")
    public List<GameEntity> getGames() {
        return gameService.getAllGames();
    }

    @GetMapping("getGameById/{id}")
    public GameEntity getGameById(@PathVariable Long id) {
        return gameService.getGameById(id);
    }

    @GetMapping("getStatusById/{gameId}")
    public ResponseEntity<String> getGameStatus(@PathVariable Long gameId) {
        GameEntity gameEntity = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("GameEntity not found"));

        StringBuilder status = new StringBuilder();
        status.append("Game Name: ").append(gameEntity.getName())
                .append(" (ID: ").append(gameEntity.getId()).append(")\n");
        status.append("Active: ").append(gameEntity.isActive()).append("\n");
        status.append("Finished: ").append(gameEntity.isFinished()).append("\n");
        status.append("Winner: ").append(gameEntity.getWinner() != null ? gameEntity.getWinner().getName() : "None").append("\n\n");

        for (PlayerEntity playerEntity : gameEntity.getPlayers()) {
            long shipCount = shipRepository.findByPlayerId(playerEntity.getId()).size();
            status.append("Player: ").append(playerEntity.getName())
                    .append(" | Ships Remaining: ").append(shipCount).append("\n");
        }

        return ResponseEntity.ok(status.toString());
    }
}
