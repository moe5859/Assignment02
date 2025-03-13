package com.microservices.assignment02.service;

import com.microservices.assignment02.entity.GameEntity;
import com.microservices.assignment02.entity.PlayerEntity;
import com.microservices.assignment02.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {
    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public GameEntity saveGame(GameEntity game) {
        if (game.getPlayers().size() == 2 && game.getCurrentTurn() == null) {
            game.setCurrentTurn(game.getPlayers().get(0));
        }
        return gameRepository.save(game);
    }

    public List<GameEntity> getAllGames() {
        return gameRepository.findAll();
    }

    public GameEntity getGameById(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Game not found"));
    }

    public void switchTurn(GameEntity game) {
        List<PlayerEntity> players = game.getPlayers();
        if (players.size() == 2) {
            PlayerEntity current = game.getCurrentTurn();
            PlayerEntity next = players.get(0).getId().equals(current.getId()) ? players.get(1) : players.get(0);
            game.setCurrentTurn(next);
        }
    }
}
