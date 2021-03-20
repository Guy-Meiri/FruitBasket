package com.guymeiri.logic.players;

import com.guymeiri.logic.GameSettings;
import com.guymeiri.logic.enums.PlayerType;
import com.guymeiri.logic.players.abstractPlayers.AbstractCheater;

import java.beans.PropertyChangeListener;

public class ThoroughCheaterPlayer extends AbstractCheater implements PropertyChangeListener {

    private int nextNumberToGuess;

    //gets notified whenever a player made a new guess
    public ThoroughCheaterPlayer(String name, PlayerType playerType, int minGuessValue, int maxGuessValue)  {
        super(name, playerType, minGuessValue, maxGuessValue);

        nextNumberToGuess = minGuessValue;

        for(int i = minGuessValue; i<= maxGuessValue; i++){
            numbersLeftToGuess.add(i);
        }
    }

    @Override
    protected int guess() {
        while( nextNumberToGuess <= GameSettings.GUESS_RANGE_MAX_VALUE && !super.numbersLeftToGuess.contains(nextNumberToGuess)){
            nextNumberToGuess++;
        }
        return nextNumberToGuess;
    }
}
