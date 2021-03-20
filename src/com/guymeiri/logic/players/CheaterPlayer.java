package com.guymeiri.logic.players;

import com.guymeiri.logic.enums.PlayerType;
import com.guymeiri.logic.players.abstractPlayers.AbstractCheater;

import java.beans.PropertyChangeListener;
import java.util.Random;

public class CheaterPlayer  extends AbstractCheater implements PropertyChangeListener {

    private Random rand = new Random();

    public CheaterPlayer(String name, PlayerType playerType, int minGuessValue, int maxGuessValue) {
        super(name, playerType, minGuessValue, maxGuessValue);

        for(int i = minGuessValue; i<= maxGuessValue; i++){
            numbersLeftToGuess.add(i);
        }
    }

    @Override
    protected int guess() {
        int indexOfNumberToGuess = rand.nextInt(numbersLeftToGuess.size());
        int guess = numbersLeftToGuess.get(indexOfNumberToGuess);
        numbersLeftToGuess.remove(indexOfNumberToGuess);
        return guess;
    }
}
