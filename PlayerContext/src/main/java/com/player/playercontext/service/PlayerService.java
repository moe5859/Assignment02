package com.player.playercontext.service;

import com.player.playercontext.entity.PlayerEntity;
import com.player.playercontext.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public PlayerEntity createPlayer(String name, Long id) {
        PlayerEntity player = new PlayerEntity();
        player.setName(name);
        PlayerEntity savedPlayer = playerRepository.save(player);

        return savedPlayer;
    }

    public PlayerEntity getPlayerById(Long id) {
        return playerRepository.findById(id).orElseThrow(() -> new RuntimeException("Player not found"));
    }

    public List<PlayerEntity> getAllPlayers() {
        return playerRepository.findAll();
    }
}
