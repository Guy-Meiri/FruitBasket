# Fruit Basket Game

This is a multiplayer game.
The goal of the players is to guess the weigh of a fruit basket.
Bonus features implemented: we finish the game not only if there were 100 attempts but also if 1500 milliseconds passed

## Main classes and their roles:

#### ConsoleUI
Recieves and validates input from the console and displays gameplay output.
Keeps a reference to a GameLogic instance and uses it to:
1. initialize user input and start each game.
2. Listen to a GameEnded event (by implementing the GameEndedListener interface) and display the results to the user.

#### GameLogic
Creates the players (using the PlayerFactory mentioned below) and starts each player on a separate thread.
Listens to guesses made by each players and raises an event whenever a new number was guessed.
Raises a GameEnded event (Listened to by the ui) at the end of the game.

#### BasePlayer 
An abstract class used as a base class for the different type of players.
Implements the Runnable interface (used to run each player in a separate thread) and runs the .

#### AbstractCheater ( extends BasePlayer)
An abstract class used as a base class for the different cheater players.
Implements PropertyChangeListener which listens to guesses events raised by the GameLogic instance.

#### Non-abstract player implementations  
  - MemoryPlayer (extends BasePlayer).  
  
  - RandomPlayer (extends BasePlayer).  
  
  - ThoroughPlayer (extends BasePlayer).  
  
  - CheaterPlayer (extends CheaterPlayer).  
  
  - ThoroughCheaterPlayer (extends CheaterPlayer).  

#### PlayerFactory
Used to create instances of actuall players according to the PlayerType enum.

#### GameSettings
Holds general game settings such as the possible number of players, the possible range of the fruit basket weight and so on.

