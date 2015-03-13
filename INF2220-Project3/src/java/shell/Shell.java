package shell;
import java.util.Scanner;
 
public abstract class Shell{
	public abstract	String 	userQuerry();
	public abstract void 	onInput(String input);

	public void inputLoop(){
		Scanner scan = new Scanner(System.in);
		String in = "";
		while(true){
			System.out.println("Enter 'exit' for program termination ");
			System.out.println(userQuerry());
		        in = scan.nextLine();
			onInput(in);
			if("exit".equals(in)) break;
		}
		System.out.println("Stopped");
		scan.close();
	}
}
