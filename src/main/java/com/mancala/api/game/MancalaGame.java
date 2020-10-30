package com.mancala.api.game;

import com.mancala.api.repository.GameData;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Representation of a game.
 * There are 13 positions. Each position number represent unique pit on the board.
 * <p>
 * Pits 1-6 are for Player 1
 * Pits 8-13 are for Player 2
 * Pit 7 - is Player 1 bank
 * Pit 0 - is Player 2 bank
 * <p>
 * ----------------<------------------
 * |   | 13 | 12 | 11 | 10 | 9 | 8 |   |  Player 2
 * |0  |--------------------------| 7 |
 * |   |1  | 2  | 3  | 4 | 5 | 6 |   |  Player 1
 * -------------->------------------
 */
@Getter
public class MancalaGame {

    private static final int NUMBER_OF_STONES = 4;
    private static final Integer PLAYER1 = 1;
    private static final Integer PLAYER2 = 2;
    private static final Map<Integer, Integer> BANKS_MAP = Map.of(
            PLAYER1, 7,
            PLAYER2, 0
    );
    private static final Map<Integer, List<Integer>> ALLOWED_POSITIONS = Map.of(
            PLAYER1, List.of(1, 2, 3, 4, 5, 6),
            PLAYER2, List.of(8, 9, 10, 11, 12, 13)
    );
    private static final int BOARD_SIZE = 14;
    private List<Integer> pits = new ArrayList<>();
    private Integer currentPlayer;


    /**
     * Initiate a new game.
     */
    public MancalaGame() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (i == 0 || i == 7) {
                pits.add(i, 0);
            } else {
                pits.add(i, NUMBER_OF_STONES);
            }
        }
        currentPlayer = PLAYER1;
    }

    /**
     * Initiate a new game from exited data.
     *
     * @param gameData existed data
     */
    public MancalaGame(GameData gameData) {
        this.currentPlayer = gameData.getCurrentPlayer();
        this.pits = gameData.getPits();
    }

    /**
     * Make a move selecting pit with the position and placing one in each of the following pits in sequence.
     *
     * @param player        the number of player
     * @param startPosition pit position to start sowing
     */
    public void sow(Integer player, Integer startPosition) {
        if (!currentPlayer.equals(player)) {
            throw new UserNotAllowedException();
        }
        if (!ALLOWED_POSITIONS.get(player).contains(startPosition)) {
            throw new PositionNotAllowedException();
        }
        Integer position = startPosition;
        Integer stones = pits.get(position);
        pits.set(position, 0);
        for (int i = 0; i < stones; i++) {
            //Skip other player's bank
            if (position.equals(BANKS_MAP.get(getOppositePlayer(player)))) {
                position++;
            }
            position = (position + 1) % BOARD_SIZE;
            pits.set(position, pits.get(position) + 1);
        }

        //Grab all opposite stones if empty pit
        if (pits.get(position).equals(1) && ALLOWED_POSITIONS.get(player).contains(position)) {
            int oppositePos = BOARD_SIZE - position;
            pits.set(BANKS_MAP.get(player), pits.get(BANKS_MAP.get(player)
                    + pits.get(oppositePos)));
            pits.set(oppositePos, 0);
        }

        //Switch players
        if (!position.equals(BANKS_MAP.get(player))) {
            currentPlayer = getOppositePlayer(player);
        }
    }

    private Integer getOppositePlayer(Integer player) {
        return PLAYER1.equals(player) ? PLAYER2 : PLAYER1;
    }

}
