package com.guymeiri.logic.players.abstractPlayers;

import com.guymeiri.logic.Game;
import com.guymeiri.logic.enums.PlayerType;

public abstract class BasePlayer implements Runnable{
    protected static int uniqId = 1; //used to generate a unique id for each player
    private int id;
    private int totalNumberOfGusses = 0;

    protected String name;
    protected PlayerType playerType;
    protected int minGuessValue;
    protected int maxGuessValue;

    public BasePlayer(String name, PlayerType playerType, int minGuessValue, int maxGuessValue){
        this.name= name;
        this.playerType = playerType;
        this.id = uniqId++;
        this.minGuessValue = minGuessValue;
        this.maxGuessValue = maxGuessValue;
    }

    public void run() {
        while (!Game.getInstance().hasGameEnded()) {
            int newGuess = guess();
            int timeToWait = Game.getInstance().handlePlayerGuess(this, newGuess);
            waitForNextTurn(timeToWait);
        }
    }

    protected abstract int guess();

    protected void waitForNextTurn(int timeToSleepInSeconds){
        if(!Game.getInstance().hasGameEnded()){
            try {
                //System.out.println(this + " going to sleep for : " + timeToSleepInSeconds + " ms"); //use for debug
                Thread.sleep(timeToSleepInSeconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString(){
        return String.format("player name: %s\n players type: %s", this.name, this.playerType.name());
    }

    public String getName() {
        return this.name;
    }

    public PlayerType getPlayerType() {
        return this.playerType;
    }
}
