import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class EvilHangman {
	public static ArrayList<String> wordList;
	private int wordLength;
	private HashSet<Character> previousGuesses;
	private TreeSet<Character> incorrectGuesses; // behaves like a hash set, but orders the entries!
	private Scanner inputScanner;
	private ArrayList<Character> currSolution;

	public EvilHangman() {this("engDictionary.txt");}
	// public EvilHangman() { this("test.txt"); }

	/*****************************************************************************************************
	 *                    CONSTRUCTORS
	 *****************************************************************************************************/

	public EvilHangman(String filename) {
		// try load the input dictionary as a word list
		try {
			wordList = dictionaryToList(filename);
		} catch (IOException e) {
			System.out.printf("Error: cannot read from the file %s.\n", filename);
			e.printStackTrace();
			System.exit(0);
		}
		// if the dictionary is loaded, initialize variables
		previousGuesses = new HashSet<>();
		incorrectGuesses = new TreeSet<>();
		inputScanner = new Scanner(System.in);
	}

	/* this function helps load input dictionary as a word list. */
	private static ArrayList<String> dictionaryToList(String filename) throws IOException {
		FileInputStream fs = new FileInputStream(filename);
		Scanner scnr = new Scanner(fs);
		ArrayList<String> wordList = new ArrayList<>();
		while (scnr.hasNext()) { wordList.add(scnr.next()); }
		return wordList;
	}

	/* This function generates an initial solution at a random length for user's first guess:
		1) gets a random word from the initial dictionary word list, and find its length;
		2) Iterate over word list to find words of the same length, and store in a new word list
		3) update the old word list to this new same length word list
		4) generate the initial currSolution */
	public void getRandomSolution() {
		int randomIndex = new Random().nextInt(wordList.size());
		String randomWord = wordList.get(randomIndex);
		this.wordLength = randomWord.length();
		ArrayList<String> sameLengthWords = new ArrayList<>();
		for (String word : wordList) {
			if (word.length() == wordLength) { sameLengthWords.add(word); }
		}
		wordList = sameLengthWords;  // update the wordList to this new same length collection
		currSolution = new ArrayList<>(Collections.nCopies(wordLength, '_'));
	}

	/*****************************************************************************************************
	 *                    START THE GAME
	 *****************************************************************************************************/

	public void start() {
		// get a collection of words with a random length, and generate the initial solution.
		getRandomSolution();

		// while there's letter not have been guessed in the current solution, prompt user to guess
		while (currSolution.contains('_')) {
			char guess = promptForGuess();
			// record the guess in previous guesses
			previousGuesses.add(guess);
			// use evilAlgo to update the new wordlist and new solution based on user's guess
			ArrayList<String> newWordList = evilAlgo(guess);
			updateCurrSolution(guess, newWordList);
			// check if
			checkGuess(guess);
		}
		System.out.printf("Congrats! You have guessed the word %s!\n", currSolution.toString());
	}

	/*****************************************************************************************************
	 *                    CORE LOGIC
	 *****************************************************************************************************/

	/**
	 * Processes the player's guess and updates the list of potential words based on the guessing strategy.
	 * This function partitions the words in the current list into different "word families" based on the guessed letter.
	 * It then selects the largest word family to continue the game, making it challenging for the player.
	 *
	 * @param guess The character guessed by the player.
	 * @return The updated list of words belonging to the chosen word family.
	 */
	public ArrayList<String> evilAlgo(char guess) {
		// Step1: First partition the dictionary words into “word families” based on the guessed letters in the words.
		TreeMap<String, TreeSet<String>> wordPatterns = new TreeMap<>();
		// Iterate over each word in the current word list to determine its pattern.
		for (String word : wordList) {
			StringBuilder sb = new StringBuilder();
			for (char ch : word.toCharArray()) {
				// Replace non-guessed characters with '-', keeping guessed characters.
				sb.append(ch != guess ? '-' : ch);  // Example: '-e--' for the guess 'e' in "belt"
			}
			String pattern = sb.toString();
			// Group words by their pattern. Create a new group if the pattern hasn't been seen before.
			wordPatterns.computeIfAbsent(pattern, k -> new TreeSet<>()).add(word);
		}

		// Step 2: find the word family (pattern) that has the most words, making it hardest for the player.
		Map.Entry<String, TreeSet<String>> maxSet = null;

		// Examine each word family to find the one with the largest number of words.
		for (Map.Entry<String, TreeSet<String>> entry : wordPatterns.entrySet()) {
			if (maxSet == null || entry.getValue().size() > maxSet.getValue().size()) {
				maxSet = entry;
			} else if (entry.getValue().size() == maxSet.getValue().size()) {
				// Handle the case where two families are of equal size.
				String currentPattern = entry.getKey();
				String maxPattern = maxSet.getKey();
				boolean currentContainsGuess = currentPattern.indexOf(guess) >= 0;
				boolean maxContainsGuess = maxPattern.indexOf(guess) >= 0;

				// a. Prefer the family without the guessed character.
				if (!currentContainsGuess && maxContainsGuess) { maxSet = entry; }

				// b. If both families contain the guessed character, prefer the one with fewer occurrences.
				else if (currentContainsGuess && maxContainsGuess) {
					long countA = currentPattern.chars().filter(ch -> ch == guess).count();
					long countB = maxPattern.chars().filter(ch -> ch == guess).count();
					if (countA < countB) { maxSet = entry; }
					else if (countA == countB) {
						// c. If have the same amount of occurrences, prefer the one with the rightmost guessed letter.
						if (currentPattern.lastIndexOf(guess) > maxPattern.lastIndexOf(guess)) { maxSet = entry; }
					}
				}
			}
		}
		if (maxSet == null) { System.out.println("No suitable pattern found."); }

        assert maxSet != null;
		// Create a new word list from the largest word family to use in the next round.
		ArrayList<String> newWordList = new ArrayList<String>(maxSet.getValue());

		return newWordList;  // Return the new list of words for the next round.
	}


	/*****************************************************************************************************
	 *                    HELPER FUNCTIONS
	 *****************************************************************************************************/

	/* Prompt user to input a letter and check if it's valid.
	 *  Return the valid input letter. */
	private char promptForGuess() {
		while (true) {
			System.out.println("Please guess a letter: \n");

			// show the current progress
			for (char c : currSolution) { System.out.print(c + " "); }
			System.out.println();

			System.out.println("Incorrect guesses so far:\n" + incorrectGuesses.toString());
			// get user's input character
			String input = inputScanner.next().toLowerCase();
			// check if the input is valid
			if (input.length() != 1) {
				System.out.println("Please enter a single character.");
			} else if (input.charAt(0) < 'a' || input.charAt(0) > 'z') {
				System.out.println("Please enter an alphabet.");
			} else if (previousGuesses.contains(input.charAt(0))) {
				System.out.printf("You've already guessed character %s.\n", input.charAt(0));
			} else {
				return input.charAt(0);	// return the valid lowercase input char
			}
		}
	}

	private void checkGuess(char guess) {
		if (!currSolution.contains(guess)) {
			System.out.printf("Sorry, there's no letter %c.\n", guess);
			incorrectGuesses.add(guess);
		} else {
			System.out.printf("Bingo, there's letter %c.\n", guess);
		}
	}

	/* Updates the current solution based on the guessed character and the new list of words. */
	private void updateCurrSolution(char guess, ArrayList<String> newWordList) {
		// Replace the current word list with the new list of words that match the guessed pattern.
		wordList = newWordList;

		// Ensure the new word list is not empty before proceeding.
		if (!newWordList.isEmpty()) {
			// Retrieve a pattern from the first word in the new word list.
			// For example, if the word is "apple" and 'p' is guessed, the pattern could be "--pp-".
			String pattern = newWordList.get(0);
			// Iterate through the pattern. If the character at a position matches the guessed character,
			// update the current solution (currSolution) at that position with the guessed character.
			for (int i = 0; i < wordLength; i++) {
				if (pattern.charAt(i) == guess) {
					currSolution.set(i, guess);
				}
			}
		}
	}
}
