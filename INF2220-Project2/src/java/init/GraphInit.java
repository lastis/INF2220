package init;
import shell.*;

class GraphInit extends Shell{

	public static void main(String[] args){
		GraphInit shell = new GraphInit();

		// This runs userQuerry and onInput
		shell.inputLoop();
	}


	public String userQuerry(){
		return "";
	}

	public void onInput(String input){
		System.out.println("Using file "+input);
		Project project = new Project(input);
		if("".equals(project.getLoopPath())){
			//Project project = new Project(input);
			project.findMinimumTime();
			project.findCriticalTasks();
		}
	}
	
}
