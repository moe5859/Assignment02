package com.game.gamecontext.service;

import com.game.gamecontext.entity.GameEntity;
import com.game.gamecontext.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {
    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public GameEntity saveGame(GameEntity game) {

        return gameRepository.save(game);
    }

    public List<GameEntity> getAllGames() {
        return gameRepository.findAll();
    }

    public GameEntity getGameById(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Game not found"));
    }
}
