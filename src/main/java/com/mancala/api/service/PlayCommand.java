package com.mancala.api.service;

import lombok.Data;

/**
 * Params required to play the game.
 */
@Data
public class PlayCommand {
    private Long gameId;
    private Integer playerNumber;
    private Integer pit;
}
