package init;
import shell.*;
import java.io.*;
import java.util.ArrayList;

class Init extends Shell{
	public ArrayList<Integer> matches = new ArrayList<Integer>();

	public static void main(String[] args){
		Init shell = new Init();
		// This runs userQuerry and onInput
		shell.inputLoop();
	}

	public String userQuerry(){
		return "Valid input is : test1, test2, test3 , all";
	}

	public void onInput(String input){
		if("test1".equals(input)) {
			run("needle1.txt","haystack1.txt");
		}
		if("test2".equals(input)) {
			run("needle2.txt","haystack2.txt");
		}
		if("test3".equals(input)) {
			run("needle3.txt","haystack3.txt");
		}
		if("all".equals(input)) {
			run("needle1.txt","haystack1.txt");
			run("needle2.txt","haystack2.txt");
			run("needle3.txt","haystack3.txt");
		}
	}


	private void run(String needleName, String haystackName){
		BufferedReader reader   = FileIO.getReader(needleName);
		if (reader == null ) {
			System.out.println("Needle not found, returning.");
			return;
		}	
		try {
			String needles[];
			String line;
			// Itterate through the needles
			needles = reader.readLine().split("\\s+");
			for (String needle : needles) {
				// Open the haystack file and start reading
				BufferedReader haystack = 
					FileIO.getReader(haystackName);
				if (haystack == null) {
					System.out.println("Haystack not found, returning.");
					return;
				}	
				// Itterate through the lines in the haystack
				int lineCnt = 0;
				line = haystack.readLine();
				while (line != null) {
					// Find needles
					int r = 0;
					while(r != -1) {
						r = findNeedle(needle.toCharArray(),
								 line.toCharArray(),
										 r);
						if(r != -1){
							// Match found!
							needleFound(needle,r,lineCnt,
								    haystackName);
							// Continue finding
							// entries but start at index
							// r + 1
							r++;
						}
					}
					line = haystack.readLine();
					lineCnt++;
				}
				if(matches.size() == 0) {
					needleNotFound(needle,haystackName);
				}
			}
		} 
		catch(IOException e) {
			System.err.format("IOException: %s%n", e);
		}
	}

	private void needleNotFound(String needle, String haystack){
		String out;
		out  = "Needle not found";
		out += "\n\tHaystack : " + haystack;
		out += "\n\tNeedle   : " + needle;
		System.out.println(out);
	}

	private void needleFound(String needle, int i, int line, 
			         String haystack){
		String out;
		out  = "Needle found!";
		out += "\n\tHaystack : " + haystack;
		out += "\n\tNeedle   : " + needle;
		out += "\n\tindex    : " + i;
		out += "\n\tline     : " + line;
		System.out.println(out);
		matches.add(i);
	}

	private int findNeedle(char[] needle, char[] haystack, int offset){
		if(needle.length > haystack.length) return -1;
		if(offset < 0) offset = 0;
		int[] badShift = new int[Character.MAX_VALUE];

		int wcc = 0;
		// Count wildcard in the needle
		for (char c : needle) {
			if (c == '_') wcc++;
		}	

		for (int i = 0; i < badShift.length; i++ ) {
			badShift[i] = needle.length - wcc;
		}

		int scan   = 0;
		int last   = needle.length - 1;
		int maxOffset = haystack.length - needle.length;

		for (int i = 0; i < last ; i++) {
			badShift[needle[i]] = last - i;
		}	

		while (offset <= maxOffset) {
			for (scan = last; needle[scan] == '_' 
				       || needle[scan] == haystack[offset+scan]; scan-- ){
				if(scan == 0) {
					// A match has been found
					return offset;
				}
			}	
			offset += badShift[haystack[offset + last]];
		}	
		return -1;
	}
}
