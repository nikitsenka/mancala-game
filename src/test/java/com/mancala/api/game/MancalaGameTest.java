package com.mancala.api.game;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MancalaGameTest {

    @Test
    void constructorShouldInitiateState() {
        MancalaGame game = new MancalaGame();
        List<Integer> pits = game.getPits();
        assertEquals(14, pits.size());
        assertArrayEquals(List.of(0,4,4,4,4,4,4,0,4,4,4,4,4,4).toArray(), pits.toArray());
        assertEquals(1, game.getCurrentPlayer());
    }

    @Test
    void sowShouldSpreadStonesFromSelectedPit() {
        MancalaGame game = new MancalaGame();
        game.sow(1, 1);
        List<Integer> pits = game.getPits();
        assertArrayEquals(List.of(0,0,5,5,5,5,4,0,4,4,4,4,4,4).toArray(), pits.toArray());
    }

    @Test
    void sowShouldSwitchTurn() {
        MancalaGame game = new MancalaGame();
        game.sow(1, 1);
        assertEquals(2, game.getCurrentPlayer());
        game.sow(2, 8);
        assertEquals(1, game.getCurrentPlayer());
    }

    @Test
    void sowShouldNotAllowPlayer1IfPlayer2Turn() {
        MancalaGame game = new MancalaGame();
        game.sow(1, 1);
        assertEquals(2, game.getCurrentPlayer());
        assertThrows(UserNotAllowedException.class, () -> game.sow(1, 1));
    }

    @Test
    void sowShouldNotAllowMoreThen2Players() {
        MancalaGame game = new MancalaGame();
        assertThrows(UserNotAllowedException.class, () -> game.sow(0, 1));
        assertThrows(UserNotAllowedException.class, () -> game.sow(2, 1));
    }

    @Test
    void sowShouldAllowIfLastStoneGoesToBank() {
        MancalaGame game = new MancalaGame();
        game.sow(1, 3);
        assertEquals(1, game.getCurrentPlayer());
        assertDoesNotThrow(() -> game.sow(1, 1));
        game.sow(2, 10);
        assertEquals(2, game.getCurrentPlayer());
        assertDoesNotThrow(() -> game.sow(2, 8));
    }

    @Test
    void sowShouldAllowRoundTrip() {
        MancalaGame game = new MancalaGame();
        game.sow(1, 6);
        assertArrayEquals(List.of(0,4,4,4,4,4,0,1,5,5,5,4,4,4).toArray(), game.getPits().toArray());
        game.sow(2, 13);
        assertArrayEquals(List.of(1,5,5,5,4,4,0,1,5,5,5,4,4,0).toArray(), game.getPits().toArray());
    }

    @Test
    void sowShouldAllowOnlyOwnerPits() {
        MancalaGame game = new MancalaGame();
        assertThrows(PositionNotAllowedException.class, () -> game.sow(1, 8));
        assertThrows(PositionNotAllowedException.class, () -> game.sow(1, 13));
        //switch player
        game.sow(1,1);
        assertThrows(PositionNotAllowedException.class, () -> game.sow(2, 1));
        assertThrows(PositionNotAllowedException.class, () -> game.sow(2, 6));
    }

    @Test
    void sowShouldGrabAllStonesFromOppositePitIfLastStoneGoesToEmptyPit() {
        MancalaGame game = new MancalaGame();
        game.sow(1, 2);
        assertArrayEquals(List.of(0,4,0,5,5,5,5,0,4,4,4,4,4,4).toArray(), game.getPits().toArray());
        game.sow(2, 13);
        assertArrayEquals(List.of(1,5,1,6,5,5,5,0,4,4,4,4,4,0).toArray(), game.getPits().toArray());
        game.sow(1, 3);
        assertArrayEquals(List.of(1,5,1,0,6,6,6,1,5,5,4,4,4,0).toArray(), game.getPits().toArray());
        game.sow(2, 8);
        assertArrayEquals(List.of(6,0,1,0,6,6,6,1,0,6,5,5,5,1).toArray(), game.getPits().toArray());
    }

}