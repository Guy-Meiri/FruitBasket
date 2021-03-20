package com.guymeiri.logic.factories;

import com.guymeiri.logic.GameSettings;
import com.guymeiri.logic.enums.PlayerType;
import com.guymeiri.logic.players.*;
import com.guymeiri.logic.players.abstractPlayers.BasePlayer;

public class PlayerFactory {
    private static PlayerFactory ourInstance = new PlayerFactory();

    public static PlayerFactory getInstance() {
        return ourInstance;
    }

    private PlayerFactory() {
    }

    public BasePlayer create(String name, PlayerType playerType){
        BasePlayer player = null;
        switch (playerType){
            case Memory:
                player = new MemoryPlayer(name, playerType, GameSettings.GUESS_RANGE_MIN_VALUE, GameSettings.GUESS_RANGE_MAX_VALUE);
                break;
            case Cheater:
                player = new CheaterPlayer(name, playerType, GameSettings.GUESS_RANGE_MIN_VALUE, GameSettings.GUESS_RANGE_MAX_VALUE);
                break;
            case ThoroughCheater:
                player = new ThoroughCheaterPlayer(name, playerType, GameSettings.GUESS_RANGE_MIN_VALUE, GameSettings.GUESS_RANGE_MAX_VALUE);
                break;
            case Random:
                player = new RandomPlayer(name, playerType, GameSettings.GUESS_RANGE_MIN_VALUE, GameSettings.GUESS_RANGE_MAX_VALUE);
                break;
            case Thorough:
                player = new ThoroughPlayer(name, playerType, GameSettings.GUESS_RANGE_MIN_VALUE, GameSettings.GUESS_RANGE_MAX_VALUE);
                break;
        }

        return player;
    }

}
