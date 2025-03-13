package com.game.gamecontext.rest;

import com.game.gamecontext.dto.GameDTO;
import com.game.gamecontext.entity.GameEntity;
import com.game.gamecontext.repository.GameRepository;
import com.game.gamecontext.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping
public class GameController {
    private final GameService gameService;
    @SuppressWarnings("unused")
    private final GameRepository gameRepository;

    public GameController(GameService  gameService, GameRepository gameRepository) {
        this.gameService = gameService;
        this.gameRepository = gameRepository;
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

}
