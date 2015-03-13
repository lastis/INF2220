package oblig1.init;
import java.util.Scanner;
 
class Shell{
	private WordDict dictionary;

	Shell(){
		dictionary = new WordDict("ordbok1_utf.txt");
		dictionary.printStatistics();
	}

	public static void main(String[] args){
		Shell shell = new Shell();
		shell.inputLoop();
	}

	public void inputLoop(){
		Scanner scan = new Scanner(System.in);
		String in = "";
		while(true){
			System.out.println("Please enter word for lookup: ");
		        in = scan.nextLine();
			if("q".equals(in)) break;
			dictionary.lookUp(in.toLowerCase());

		}
		System.out.println("Stopped");
		scan.close();
	}
}
