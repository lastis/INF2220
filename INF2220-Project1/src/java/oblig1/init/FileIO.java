package oblig1.init;
import java.io.*;

class FileIO {
	private static BufferedReader reader = null;

	public 	static BufferedReader getReader(String fileName){
		InputStream		is;
		InputStreamReader 	isr;
		try { 
			is 	= FileIO.class.getResourceAsStream("/"+fileName);
			isr 	= new InputStreamReader(is, "UTF-8");
			reader 	= new BufferedReader(isr);
		} 
		catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		}
		return reader;
	}
}
