package com.mancala.api.game;

/**
 * Thrown when if it is another player turn.
 */
public class UserNotAllowedException extends RuntimeException {

    /**
     * Build exception with the base message.
     */
    public UserNotAllowedException() {
        super("It is another user turn.");
    }
}
