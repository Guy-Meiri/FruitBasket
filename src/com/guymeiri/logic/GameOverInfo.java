package com.guymeiri.logic;

import com.guymeiri.logic.enums.GameOverReason;
import com.guymeiri.logic.enums.PlayerType;
import com.guymeiri.logic.players.abstractPlayers.BasePlayer;

public class GameOverInfo {
    public GameOverReason gameOverReason;
    public String playerName;
    public PlayerType playerType;
    public int bestGuess;
    public int totalNumberAttempsMade;

    public GameOverInfo(GameOverReason gameOverReason, BasePlayer winner, int bestGuess, int totalNumberAttempsMade) {
        this.gameOverReason = gameOverReason;
        this.playerName = winner.getName();
        this.playerType = winner.getPlayerType();
        this.bestGuess = bestGuess;
        this.totalNumberAttempsMade = totalNumberAttempsMade;
    }
}
