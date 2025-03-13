package com.microservices.assignment02.service;

import com.microservices.assignment02.entity.GameEntity;
import com.microservices.assignment02.entity.PlayerEntity;
import com.microservices.assignment02.repository.GameRepository;
import com.microservices.assignment02.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;

    public PlayerService(PlayerRepository playerRepository, GameRepository gameRepository) {
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
    }

    public PlayerEntity createPlayer(String name, Long id) {



        GameEntity game = gameRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("no Game found with that ID"));

        PlayerEntity player = new PlayerEntity();
        player.setName(name);
        player.setGame(game);
        PlayerEntity savedPlayer = playerRepository.save(player);
        System.out.println("kaputt bruder 1");
        game.getPlayers().add(savedPlayer);
        System.out.println("kaputt bruder 2");
        if (game.getPlayers().size() == 2 && game.getCurrentTurn() == null) {
            game.setCurrentTurn(game.getPlayers().getFirst());
            gameRepository.save(game);
        }
        System.out.println("kaputt bruder 3");
        return savedPlayer;
    }

    public PlayerEntity getPlayerById(Long id) {
        return playerRepository.findById(id).orElseThrow(() -> new RuntimeException("Player not found"));
    }

    public List<PlayerEntity> getAllPlayers() {
        return playerRepository.findAll();
    }
}
