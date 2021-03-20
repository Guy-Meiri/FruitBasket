package com.guymeiri.logic;

import com.guymeiri.logic.enums.GameOverReason;
import com.guymeiri.logic.enums.PlayerType;
import com.guymeiri.logic.factories.PlayerFactory;
import com.guymeiri.logic.interfaces.GameEndedListener;
import com.guymeiri.logic.players.abstractPlayers.BasePlayer;
import com.guymeiri.logic.utils.Utils;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

public class Game{

    private int totalNumberOfGuessesMade = 0;
    private boolean isGameEnded = false;
    private int weightToGuess;

    private List<BasePlayer> players = new ArrayList<>();
    private List<PropertyChangeListener> guessListeners = new ArrayList<>();
    private GameEndedListener ui;
    private Set<Integer> numbersAlreadyGuessed = new HashSet<>();

    private Random rand = new Random();
    private BasePlayer winner = null;
    private int closestGuess = Integer.MAX_VALUE;
    private BasePlayer closestPlayer;

    private static Game ourInstance = new Game();


    public static Game getInstance() {
        return ourInstance;
    }

    private Game() {
    }

    public void startGame() {
        //register a callback function that will end the game after MAX_GAME_TIME_MILLIS (if it didn't already end by this point)
        Utils.setTimeout(() -> Game.getInstance().updateGameHasEndedDueToTimeout(), GameSettings.MAX_GAME_TIME_MILLIS);

        //start players
        players.stream().forEach(player -> new Thread(player).start());
    }


    public void initGame(Map<String, PlayerType> playersInfo, GameEndedListener UI , int weightToGuess) {
        reset();
        createPlayers(playersInfo);
        this.weightToGuess = weightToGuess;
        this.ui = UI;
    }

    private void reset() {
        this.winner = null;
        this.closestGuess = Integer.MAX_VALUE;
        this.closestPlayer = null;
        this.isGameEnded = false;
    }
    private void createPlayers(Map<String, PlayerType> playersInfo) {
        for(Map.Entry<String, PlayerType> player: playersInfo.entrySet()){
            BasePlayer newPlayer = PlayerFactory.getInstance().create(player.getKey(), player.getValue());
            players.add(newPlayer);
        }
        registerCheaterPlayers();
    }

    private void updateGameHasEndedDueToTimeout() {
        if(!hasGameEnded()){
            endGame();
        }
    }

    public int handlePlayerGuess(BasePlayer player, Integer guess){

        //handling of an edge case resulting from each player running in a seperate thread
        //in which a player made a guess after another player just won
        if(isGameEnded) return GameSettings.GAME_ENDED;

        handleNewGuess(guess);

        endGameIfNeeded(guess, player);

        updateClosestPlayerIfNeeded(guess, player);

        return calcGuessDistanceFromSolution(guess);
    }

    private void updateClosestPlayerIfNeeded(int guess, BasePlayer player) {
        if(Math.abs(weightToGuess - guess) < Math.abs(this.closestGuess - guess)){
            this.closestGuess = guess;
            this.closestPlayer = player;
        }
    }

    private void endGameIfNeeded(Integer guess, BasePlayer player) {
        if(weightToGuess == guess){
            winner = player;
            endGame();
        }
        else if(totalNumberOfGuessesMade == (GameSettings.MAX_NUMBER_GUESSES)){
            endGame();
        }
    }

    private void handleNewGuess(int guess) {

        totalNumberOfGuessesMade++;

        if(!numbersAlreadyGuessed.contains(guess)){
            numbersAlreadyGuessed.add(guess);
            notifyGuessWasMade(guess);
        }
    }

    private void endGame() {
        isGameEnded = true;
        GameOverReason gameOverReason;
        if(winner != null){
            gameOverReason = GameOverReason.WINNER_FOUND;
        }else if(totalNumberOfGuessesMade == GameSettings.MAX_NUMBER_GUESSES){
            gameOverReason = GameOverReason.MAX_NUM_OF_TURNS;
        }else{
            gameOverReason = GameOverReason.TIMEOUT;
        }

        ui.onGameEnded(new GameOverInfo(gameOverReason, gameOverReason.equals(GameOverReason.WINNER_FOUND)? winner: closestPlayer, this.closestGuess, this.totalNumberOfGuessesMade));
    }

    private void registerCheaterPlayers() {
        for(BasePlayer p: players ){
            if(p instanceof PropertyChangeListener ){
                guessListeners.add((PropertyChangeListener)p);
            }
        }
    }

    //this method notifies whoever is interested (in our case, cheater players) that a guess was made
    private void notifyGuessWasMade(Integer numberGuessed){
        for(PropertyChangeListener listener: guessListeners){
            listener.propertyChange(new PropertyChangeEvent(this, null, null, numberGuessed));
        }
    }

    private int calcGuessDistanceFromSolution(Integer guess) {
        return Math.abs(weightToGuess - guess);
    }

    public synchronized boolean hasGameEnded() {
        return isGameEnded;
    }

}
