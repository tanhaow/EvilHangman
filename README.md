# Evil Hangman Project
Github repo link: https://github.com/tanhaow/EvilHangman.git

## Overview
Evil Hangman is a variation of the traditional Hangman game. Unlike the standard game where the word to be guessed remains constant, Evil Hangman changes the word throughout the game as long as it fits the player's previous guesses. This project implements Evil Hangman in Java, offering a more challenging word-guessing experience.

## Features
- **Evil Algorithm:** Uses a special algorithm (`evilAlgo`) to select the largest word family based on the player's guesses, secretly adapting the target word based on the player's guesses to maintain a group of words that fit all previous guesses.
- **Customized Initial Word Length:** The game begins with a randomly chosen word length, increasing the unpredictability.
- **Interactive Gameplay:** Players are continuously prompted for new guesses and shown their progress. Also, both correct and incorrect guesses are recorded and displayed to the player.

## Components
The project is comprised of the following main components:
1. `EvilHangman.java`: Contains the core game logic, including word selection, guess processing, and game flow control.
2. Other associated files like `EvilHangmanRunner.java` and `EvilHangmanTest.java` for running and testing the game.
3. `engDictionary.txt`: A default English dictionary file used for word selection.

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

## Contact
- Hao Tan: [tanhao@seas.upenn.edu](mailto:tanhao@seas.upenn.edu)
