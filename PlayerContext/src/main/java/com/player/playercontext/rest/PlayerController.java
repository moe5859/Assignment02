package com.player.playercontext.rest;

import com.player.playercontext.dto.PlayerDTO;
import com.player.playercontext.entity.PlayerEntity;
import com.player.playercontext.service.PlayerService;
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
