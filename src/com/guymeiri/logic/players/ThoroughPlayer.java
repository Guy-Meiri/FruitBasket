package com.guymeiri.logic.players;

import com.guymeiri.logic.enums.PlayerType;
import com.guymeiri.logic.players.abstractPlayers.BasePlayer;

public class ThoroughPlayer extends BasePlayer {

    private int nextNumberToGuess;

    public ThoroughPlayer(String name, PlayerType playerType, int minGuessValue, int maxGuessValue) {
        super(name, playerType, minGuessValue, maxGuessValue);
        nextNumberToGuess = minGuessValue;
    }

    @Override
    protected int guess() {
        return nextNumberToGuess++;
    }
}
