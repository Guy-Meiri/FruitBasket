package com.guymeiri.ui;

import com.guymeiri.logic.Game;
import com.guymeiri.logic.GameOverInfo;
import com.guymeiri.logic.GameSettings;
import com.guymeiri.logic.enums.GameOverReason;
import com.guymeiri.logic.enums.PlayerType;
import com.guymeiri.logic.enums.PlayerTypeHelper;
import com.guymeiri.logic.interfaces.GameEndedListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class ConsoleUI implements GameEndedListener {

    private Integer numPlayers;
    private Scanner scanner = new Scanner(System.in);
    private Map<String, PlayerType> playersInput = new HashMap<>();
    private Game gameLogic = Game.getInstance();

    public void init(){
        System.out.println("\n***********\nFRUIT BASKET GAME\n***********\n");
        this.numPlayers = getNumberOfPlayersFromUser();
        this.initPlayerInfo();
        int weightToGuess = getRandomWeightToGuess();
        System.out.println("\n***********\nSTARTING GAME\n***********");
        System.out.println(String.format("Basket weight is: %d\n", weightToGuess));
        this.gameLogic.initGame(playersInput, this,weightToGuess);
        this.gameLogic.startGame();
    }

    private void initPlayerInfo() {
        for(int i = 1; i<= numPlayers; i++){
            getSinglePlayerInfoFromUser(i);
        }
        System.out.println("");
    }

    private void getSinglePlayerInfoFromUser(int playerNumber) {
        Integer number = null;
        do {
            if(number != null) {
                System.out.println("input entered is invalid");
            }
            System.out.println(String.format("\nSelect player %d type:", playerNumber));
            for(PlayerType playerType: PlayerType.values()) {
                System.out.println(String.format("%s for a %s player", playerType.ordinal(), playerType.name()));
            }
            System.out.println(" ");

            while (!scanner.hasNextInt()) {
                String input = scanner.next();
                System.out.printf("\"%s\" is not a valid number.\n", input);

            }
            number = scanner.nextInt();
        } while (number < 0 || number > 4);
        PlayerType playerType =PlayerTypeHelper.fromInt(number);

        System.out.println(String.format("Select a name for player %d: ", playerNumber));
        String name = scanner.next();

        playersInput.put(name, playerType);

    }

    private Integer getNumberOfPlayersFromUser() {
        Integer number = null;
        do {
            if(number != null) {
                System.out.println("input entered is invalid");
            }
            System.out.println(String.format("Enter the number of players (%d - %d):", GameSettings.MIN_NUM_PLAYERS, GameSettings.MAX_NUM_PLAYERS ));
            while (!scanner.hasNextInt()) {
                String input = scanner.next();
                System.out.printf("\"%s\" is not a valid number.\n", input);
            }
            number = scanner.nextInt();
        } while (number < 2 || number > 8);


        return number;
    }

    private int getRandomWeightToGuess() {
        return new Random().nextInt(GameSettings.GUESS_RANGE_MAX_VALUE - GameSettings.GUESS_RANGE_MIN_VALUE)+ GameSettings.GUESS_RANGE_MIN_VALUE;
    }

    @Override
    public void onGameEnded(GameOverInfo gameOverInfo) {
        System.out.println("\n***********\nGAME OVER\n***********\n");
        if(gameOverInfo.gameOverReason.equals(GameOverReason.WINNER_FOUND)){
            System.out.println(String.format("Winning player: %s (%s player)\nTotal number of attempts: %d", gameOverInfo.playerName, PlayerTypeHelper.fromInt(gameOverInfo.playerType.ordinal()), gameOverInfo.totalNumberAttempsMade));
        }else{
            System.out.println("There was no winner\n");
            if(gameOverInfo.gameOverReason.equals(GameOverReason.MAX_NUM_OF_TURNS)){
                System.out.println(String.format("Max number of attempts (%d) were made", GameSettings.MAX_NUMBER_GUESSES));
            }else if(gameOverInfo.gameOverReason.equals(GameOverReason.TIMEOUT)){
                System.out.println(String.format("%d milliseconds has passed", GameSettings.MAX_GAME_TIME_MILLIS));
            }

            System.out.println(String.format("Player with the closest guess is: %s with a guess of %d", gameOverInfo.playerName, gameOverInfo.bestGuess));
        }

        playAgain();
    }

    private void playAgain() {
        System.out.println("\n---------------------------------------------");
        System.out.println("Press y to play again or any other key to exit:");
        String rawAnswer = scanner.next();
        String answer = rawAnswer.trim().toLowerCase();
        if(answer.charAt(0) == 'y'){
            playersInput.clear();
            init();
        }else{
            System.out.println("\nGoodbye!");
        }
    }

}
