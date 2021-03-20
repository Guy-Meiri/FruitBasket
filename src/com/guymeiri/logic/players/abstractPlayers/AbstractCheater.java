package com.guymeiri.logic.players.abstractPlayers;

import com.guymeiri.logic.enums.PlayerType;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCheater extends BasePlayer implements PropertyChangeListener {

    protected  List<Integer> numbersLeftToGuess = new ArrayList<>();

    public AbstractCheater(String name, PlayerType playerType, int minGuessValue, int maxGuessValue) {
        super(name, playerType, minGuessValue, maxGuessValue);
    }

    protected void removeFromFutureGuess(int numberGuessed){
        int ind = numbersLeftToGuess.indexOf(numberGuessed);
        if(ind != -1){
            numbersLeftToGuess.remove(ind);
        }
    }

    //gets notified whenever a player made a new guess
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        int numberGuessed = (int)evt.getNewValue();
        this.removeFromFutureGuess(numberGuessed);
    }
}
