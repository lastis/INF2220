package oblig1.init;
import java.io.*;
import oblig1.binarytree.*;

class WordDict {

	private BinarySearchTree<String> list;

	WordDict(String fileName){
		list = new BinarySearchTree<String>();
		addWords(fileName);
	} 

	public void printStatistics(){
		list.printStatistics();
		System.out.println("The first word is :\t" + list.findMin());
		System.out.println("The last word is  :\t" + list.findMax());
		System.out.println("");
	}

	public boolean lookUp(String word){
		boolean contains = list.contains(word);
		String  output;
		if(contains) {
			output = "The word \"" + word + 
				"\" is contained in the dictionary.";
			System.out.println(output);
		}
		else {
			output = "The word \"" + word + "\" is not contained "+
				"in the dictionary.\nWill now commence simple "+
				"search for similar words.";
			System.out.println(output);
			checkSimilarWords(word);
			
		}
		System.out.println();
		return contains;
	}

	public void checkSimilarWords(String word){
		long start = System.currentTimeMillis();    
		String[] similarWords = SpellCheck.similarWords(word);
		String output = "Similar words contained in the dictinary:";
		System.out.println(output);
		String containedWords = "";
		String tmp;
		int wordCount = 0;
outerloop:	for(int i = 0; i < similarWords.length; i++){
			tmp = similarWords[i];
			// Check for duplicate entries
			for (int j = 0; j < i; j++){
				if(tmp.equals(similarWords[j])){
					continue outerloop;
				}	
			}	
			if (list.contains(tmp)){
				containedWords += tmp + " ";
				wordCount++;
			}
		}
		long 	elapsedTime = System.currentTimeMillis() - start;
		double 	time = elapsedTime/1000.0;
		if("".equals(containedWords)) containedWords = "None";
		System.out.println(containedWords);
		System.out.println("Number of similar words in dict.:\t"
			       	+ wordCount);
		System.out.println("Time spent searching:\t\t\t" + time);
	}

	public void addWords(String fileName){
		BufferedReader reader = FileIO.getReader(fileName);
		String line = null;
		try{
			line = reader.readLine();
			while (line != null){
				for(String word : line.split(" ")){
					list.add(word);
				}
				line = reader.readLine();
			}
		}
		catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		}
	}
}
