package shell;
import java.io.*;

public class FileIO {
	private static BufferedReader reader = null;

	public 	static BufferedReader getReader(String fileName){
		InputStream		is;
		InputStreamReader 	isr;
		try { 
			is = FileIO.class.getResourceAsStream("/"+fileName);
			if(is == null) {
				String error  = "File: " + fileName + " not found. ";
				error 	     += "Returning null";
				System.out.println(error);
				return null;
			}			
			isr 	= new InputStreamReader(is, "UTF-8");
			reader 	= new BufferedReader(isr);
		} 
		catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		}
		return reader;
	}
}
