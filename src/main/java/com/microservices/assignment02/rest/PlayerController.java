package com.microservices.assignment02.rest;

import com.microservices.assignment02.dto.PlayerDTO;
import com.microservices.assignment02.entity.PlayerEntity;
import com.microservices.assignment02.service.PlayerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping("createPlayer")
    public PlayerEntity createPlayer(@RequestBody PlayerDTO request) {
        return playerService.createPlayer(request.getName(), request.getGameId());
    }

    @GetMapping("getPlayerById/{id}")
    public PlayerEntity getPlayerById(@PathVariable Long id) {
        return playerService.getPlayerById(id);
    }

    @GetMapping("getAllPlayers")
    public List<PlayerEntity> getAllPlayers() {
        return playerService.getAllPlayers();
    }
}
