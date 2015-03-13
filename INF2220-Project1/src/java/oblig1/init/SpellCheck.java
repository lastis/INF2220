package oblig1.init;

class SpellCheck {
	public static String[] similarWords(String word){
		char[] chars = word.toCharArray();
		String charSwapped  	= swapCharacters(chars);
		String charReplaced 	= replaceChars(chars);
		String charRemoved  	= removeChars(chars);
		String charAdded	= addChars(chars);
		String allWords		= charSwapped + charReplaced 
			+ charRemoved + charAdded;
		String[] wordArray	= allWords.split(" ");
		return wordArray;
	}

	private static String addChars(char[] word){
		String 	words = "";
		char[]  additions = "abcdefghijklmnopqrstuvwxyzæøå".toCharArray();
		for (int i = 0; i < additions.length; i++){
			words += addOneChar(word, additions[i]);
		}	
		return words;

	}

	private static String addOneChar(char[] word, char letter){
		String 	words = "";
		char[] 	newWord;
		for (int i = 0; i < word.length + 1; i++){
			newWord    = new char[word.length + 1];
			newWord[i] = letter;
			for (int j = 0; j < word.length + 1; j++){
				if (j < i) newWord[j] = word[j];
				if (j > i) newWord[j] = word[j-1];
			}	
			words 		+= " " + new String(newWord);
		}	
		return words;
	}

	private static String removeChars(char[] word){
		String 	words = "";
		char[] 	tmpWord;
		for (int i = 0; i < word.length; i++){
			tmpWord  	= word.clone();
			tmpWord[i] 	= 0;
			words 		+= " " + new String(tmpWord);
		}	
		return words;
	}


	private static String swapCharacters(char[] word){
		String 	words = new String(word);
		char[] 	tmpWord;
		char 	tmpLetter;
		for (int i = 0; i < word.length - 1 ; i++){
			tmpWord  = word.clone();
			tmpLetter = tmpWord[i];
			tmpWord[i]   = tmpWord[i+1];
			tmpWord[i+1] = tmpLetter;
			words += " " + new String(tmpWord);
		}	
		return words;
	}

	private static String replaceChars(char[] word){
		String 	words = "";
		char[] 	tmpWord;
		char[]  replacements = "abcdefghijklmnopqrstuvwxyzæøå".toCharArray();
		for (int i = 0; i < replacements.length; i++){
			for (int j = 0; j < word.length; j++){
				tmpWord    = word.clone();
				tmpWord[j] = replacements[i];
				words += " " + new String(tmpWord);
			}	
		}	
		return words;
	}
}
