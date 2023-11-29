# Evil Hangman Project

## Overview
Evil Hangman is a variation of the traditional Hangman game. Unlike the standard game where the word to be guessed remains constant, Evil Hangman changes the word throughout the game as long as it fits the player's previous guesses. This project implements Evil Hangman in Java, offering a challenging and dynamic word-guessing experience.

## Features
- **Dynamic Word Selection:** The program adapts the target word based on the player's guesses, maintaining words that fit all previous guesses.
- **Customized Initial Word Length:** The game begins with a randomly chosen word length, increasing the unpredictability.
- **Tracking of Guesses:** Stores both correct and incorrect guesses, displaying them to the player.
- **Evil Algorithm:** Uses a special algorithm (`evilAlgo`) to select the largest word family based on the player's guesses, making the game more challenging.
- **Interactive Gameplay:** Players are continuously prompted for new guesses and shown their progress.

## Components
The project is comprised of the following main components:
1. `EvilHangman.java`: Contains the core game logic, including word selection, guess processing, and game flow control.
2. Other associated files like `EvilHangmanRunner.java` and `EvilHangmanTest.java` (not detailed here) for running and testing the game.
3. `engDictionary.txt`: A text file with a list of English words used for word selection.

## How to Run
1. **Compile the Java Files:**
    - Compile the `.java` files with a Java compiler.
      ```
      javac EvilHangman.java EvilHangmanRunner.java EvilHangmanTest.java
      ```
2. **Run the Game:**
    - Start the game by executing `EvilHangmanRunner`.
      ```
      java EvilHangmanRunner
      ```

## How to Play
- The game starts with a randomly selected word length.
- The player guesses letters. The game reveals positions of the guessed letter if it exists in the word.
- The game uses `evilAlgo` to adjust the word list after each guess, ensuring it remains challenging.
- Progress is shown to the player after each guess, along with the letters guessed so far.

## Contributions
Contributions are welcome. Please adhere to standard Java project contribution guidelines.

## License
[Specify License here, if applicable]
