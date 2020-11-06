import java.io.*;
import java.util.*;
import javax.swing.JFileChooser;

/**
 * CS 1103 Programming Assignment-5 (By: Ashik Acharya)
 * 
 * This program acts as a spell checker for a text file.
 * This program will ask users for two files, one that will be
 * used as a dictionary while other will be used for testing
 * the correctness of the words and receiving possible correct 
 * words as suggestions.
 * 
 *  Thus we have options in choosing dictionary as well.
 */
public class SpellChecker {

	/**
	 * asks the user for file inputs and calls the
	 * routines for spelling checks.
	 */
	public static void main(String[] args) {
		
		/* we will start main program by reading the words
		 *  from words.txt and storing them in a HashSet<String>.
		 *  (In our program, user will be asked to choose the dictionary as 
		 *  well as file to check spelling. 
		 */
		Collection<String> dictionary = new HashSet<>();
		
		System.out.println("Choose the file to be used as a dictionary.\n");
		dictionary = createDictionary();
		
		if (dictionary.size() == 0) {
			System.out.println("No dictionary from which to perform spell.");
			System.exit(0);
		}
		
		System.out.println("Choose the file to check spelling\n");
		
		File file = getInputFileNameFromUser("Select File to check spelling. ");
		
		if (file != null) {
			System.out.println("List of possibly misspelled words"
					+ " and some possible corrections:\n");
			spellCheckWordsFromFile(file, dictionary);
		} else {
			System.out.println("No file selected to spell check.");
		}
	}
	
	/**
	 * Receives a file chosen by user to be implemented as a dictionary.
	 * The words in the dictionary file should be separated by one or more
	 * non-letter characters.
	 */
	private static Collection<String> createDictionary()  {
		
		Collection<String> dict = new HashSet<>();
		
		File file = getInputFileNameFromUser("Select Dictionary File");
		if (file != null) {
			try {
				Scanner filein = new Scanner (file);				
				while (filein.hasNext()) {
					String word = filein.next();
					word = word.toLowerCase();
					dict.add(word);
				}		
				filein.close();
				
			} catch (FileNotFoundException e) {
				System.out.println("Can't find dictionary file.");
			}
		}
		
		return dict;
	}
	
	/**
	 * dict will be used to compare against words from the file.
	 * 
	 * The words in the file to be spell checked should be separated
	 * by one or more non-letter characters.  If a word is not in the
	 * dictionary supplied, possible correct suggestions are printed
	 * to standard output for each word in the input that does not
	 * match a word in the dictionary.
	 * 
	 *fil refers to the file object that has been checked to ensure it
	 *     is not null.
	 *dict refers to the dictionary to use as the correct spellings
	 *     of English words.
	 */
	private static void spellCheckWordsFromFile(File fil, Collection<String> dict) {
		
		/* 
		 * Store words that have already been output in a set so
		 * the program will not output the same misspelled word
		 * more than once.
		 */
		Collection<String> wordsToOutput = new HashSet<>();
		
		try {
			Scanner in = new Scanner(fil);
			/*"[^a-zA-Z]+" is a regular expression that matches any 
			 * sequence of one or more non-letter characters.*/
			in.useDelimiter("[^a-zA-Z]+");
			
			while (in.hasNext()) {
				String word = in.next();
				word = word.toLowerCase();
				
				if (!dict.contains(word)) {
					if (!wordsToOutput.contains(word)) {
						outputSuggestions(word, dict);
						wordsToOutput.add(word);
					}
				}
			}
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("Can't find file to spell check words.");
		}
	}
	
	/**
	 * Prints potentially misspelled words along with possible corrections.
	 */
	private static void outputSuggestions(String badWord, Collection<String> dict) {
		
		
		TreeSet<String> suggestions = new TreeSet<>();
		
		suggestions.addAll(corrections(badWord, dict));
		
		if (suggestions.size() == 0) {
			System.out.println(badWord + ": (no suggestions)");
		} else {
			
			System.out.print(badWord + ": ");
			String firstWord = suggestions.first();  
			System.out.print(firstWord);
			for (String word : suggestions.tailSet(firstWord, false)) {
				System.out.print(", " + word);
			}
			System.out.println();
		}
	}
	
	/**
	 * Collects all the possible corrections to a misspelled word.
	 * badWord refers to the word that is potentially misspelled.
	 * dict refers to the dictionary to use as the correct spellings of English words.
	 */
	private static Collection<String> corrections(String badWord, Collection<String> dict) {
		
		Collection<String> corrections = new TreeSet<>();
		
		// Variables to hold the five possible corrections for the program.
		String deletedLetters;
		String changedLetters;
		String insertedLetters;
		String swappedLetters;
		String spaceInserted;
		
		for (int i = 0; i < badWord.length(); i++) {
			
			if (i < badWord.length() - 1) {
				char[] c = badWord.toCharArray();
				char temp = c[i];
				c[i] = c[i + 1];
				c[i + 1] = temp;
				
				swappedLetters = new String(c);
				
				if (dict.contains(swappedLetters)) {
					corrections.add(swappedLetters);
				}
			}
			
			
			if (dict.contains(badWord.substring(0,  i)) && dict.contains(badWord.substring(i))) {
				spaceInserted = badWord.substring(0, i) + ' ' + badWord.substring(i);
				corrections.add(spaceInserted);
			}
			
			
			for (char ch = 'a'; ch <= 'z'; ch++) {
				
				deletedLetters = badWord.substring(0, i) + badWord.substring(i + 1);
				if (dict.contains(deletedLetters)) {
					corrections.add(deletedLetters);
				}
				
				changedLetters = badWord.substring(0, i) + ch + badWord.substring(i + 1);
				if (dict.contains(changedLetters)) {
					corrections.add(changedLetters);
				}
				
				insertedLetters = badWord.substring(0, i) + ch + badWord.substring(i);
				if (dict.contains(insertedLetters)) {
					corrections.add(insertedLetters);
					
				} else if (dict.contains(badWord + ch)) {
					corrections.add(badWord + ch);
				}
			}
		}
		
		return corrections;
	}
	
    /**
     * Lets the user select an input file using a standard file
     * selection dialog box.  If the user cancels the dialog
     * without selecting a file, the return value is null.
     */
    private static File getInputFileNameFromUser(String title) {
       JFileChooser fileDialog = new JFileChooser();
       fileDialog.setDialogTitle(title);
       int option = fileDialog.showOpenDialog(null);
       if (option != JFileChooser.APPROVE_OPTION)
          return null;
       else
          return fileDialog.getSelectedFile();
    }
}

	