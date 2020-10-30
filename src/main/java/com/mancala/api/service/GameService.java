package com.mancala.api.service;

import com.mancala.api.game.MancalaGame;
import com.mancala.api.repository.GameData;
import com.mancala.api.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Main game logic.
 */
@Component
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    /**
     * Initiate a new game.
     *
     * @return new game data
     */
    public GameData init() {
        return gameRepository.save(copyGameData(new MancalaGame()));
    }

    /**
     * Play the existed game using command params.
     *
     * @param playCommand params to play
     * @return the updated data
     */
    public GameData play(PlayCommand playCommand) {
        GameData gameData = gameRepository.findById(playCommand.getGameId()).get();
        MancalaGame game = new MancalaGame(gameData);
        game.sow(playCommand.getPlayerNumber(), playCommand.getPit());
        return gameRepository.save(copyGameData(game));
    }

    private GameData copyGameData(MancalaGame game) {
        GameData gameData = new GameData();
        gameData.setCurrentPlayer(game.getCurrentPlayer());
        gameData.setPits(game.getPits());
        return gameData;
    }
}
