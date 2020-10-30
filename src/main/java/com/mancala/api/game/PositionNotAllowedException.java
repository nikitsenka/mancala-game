package com.mancala.api.game;

/**
 * Thrown when pit position is not allowed for the player.
 * Player should use stones only from the right side of the board.
 */
public class PositionNotAllowedException extends RuntimeException {

    /**
     * Build exception with the base message.
     */
    public PositionNotAllowedException() {
        super("Pit position is not allowed.");
    }
}
