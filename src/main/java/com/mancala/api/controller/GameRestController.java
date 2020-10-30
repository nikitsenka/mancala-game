package com.mancala.api.controller;

import com.mancala.api.service.GameService;
import com.mancala.api.repository.GameData;
import com.mancala.api.service.PlayCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest mancala controller.
 */
@CrossOrigin
@RestController
@RequestMapping("/api")
public class GameRestController {

    @Autowired
    private GameService gameService;

    /**
     * Initiates new game.
     *
     * @return game id
     */
    @PostMapping("/mancala/game/init")
    public ResponseEntity<GameData> init() {
        return ResponseEntity.ok().body(gameService.init());
    }

    /**
     * Make a move for the game.
     *
     * @param playCommand params to play a game
     */
    @PostMapping("/mancala/play")
    public ResponseEntity<GameData> play(@RequestBody PlayCommand playCommand) {
        return ResponseEntity.ok().body(gameService.play(playCommand));
    }
}
