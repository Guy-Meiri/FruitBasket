package com.guymeiri.logic.players;

import com.guymeiri.logic.Game;
import com.guymeiri.logic.GameSettings;
import com.guymeiri.logic.enums.PlayerType;
import com.guymeiri.logic.players.abstractPlayers.BasePlayer;

import java.util.Random;

public class RandomPlayer extends BasePlayer {

    private Random rand = new Random();

    public RandomPlayer(String name, PlayerType playerType, int minGuessValue, int maxGuessValue) {
        super(name, playerType, minGuessValue, maxGuessValue);
    }

    @Override
    protected int guess() {
        int guess = getRandomNumberInRange();
        return Game.getInstance().handlePlayerGuess(this, guess);
    }

    private int getRandomNumberInRange() {
        return rand.nextInt(GameSettings.GUESS_RANGE_MAX_VALUE- GameSettings.GUESS_RANGE_MIN_VALUE)+ GameSettings.GUESS_RANGE_MIN_VALUE;
    }


}
