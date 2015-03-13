package init;
import	graph.*;
import	shell.*;
import	java.io.*;

public class Project extends Graph{
	int	mMinTime = 0;

	// 	Variable used by nodes
	int	mTotalStaff = 999;
	int	mWorkingStaff = 0;
	boolean	mChanged = false;
	int	mCurTime = 0;

	Project(String fileName){
		// This does nothing
		super();
		// This initiates graph and adds all the nodes
		handleFile(fileName);
		readyGraph();
	}

	public void	taskStarted(int staff, int time){
		if(mCurTime != time) {
			mChanged = true;
		}
		mWorkingStaff += staff;
	}

	public	void	taskFinished(int staff, int time){
		if(mCurTime != time) {
			mChanged = true;
		}
		mWorkingStaff -= staff;
	}

	public	void	print(){
		printNodeNabors();
		/*
		Task[]	tasks = getTasks();
		for (Task task : tasks) {
			task.print();
		}
		*/
	}
	private	void 	resetTasks(){
		Task[] tasks = getTasks();
		for (Task task : tasks) {
			task.reset();
		}
	}

	public	int	findMinimumTime(){
		printON();
		mMinTime = doProject();
		System.out.print  ("*** Mimimum time of the project is ");
		System.out.println(mMinTime+" ***");
		return mMinTime;
	}

	private	boolean	changed(){
		return mChanged;
	}

	public int	doProject(){
		int T 	 = 0;
		Task[] tasks = getTasks();
		for (Task task : tasks) {
			// Ask tasks that have no predecessors
			// to get ready, at T = 0
			task.suggestStart(0,this);
		}
		boolean pTask = false;
		while (!pTask) {
			T++;
			pTask = true;
			for (Task task : tasks) {
				// If previous was done, then ask the next
				// task if it is done also. If one task is
				// not done than it cannot become true again.
				if (pTask == true) pTask = task.isDone();
				// Ask tasks that have no predecessors
				// to get ready, at T = 0
				task.continueTask(T,this);
				//task.continueTask(T,mGraph);
			}
			if(shouldPrint() && changed()){
				System.out.print("Current Working Staff : ");
				System.out.println(mWorkingStaff);
				mChanged = false;

			}
			// Only if all of the tasks are done will 
			// pTask be true at this point
			// pTask == true -> break
		}
		// reset tasks when we are done
		resetTasks();
		return T;
	}

	public	void	findCriticalTasks(){
		printOFF();
		//mGraph.printOFF();
		Task[] tasks = getTasks();
		if (mMinTime == 0) findMinimumTime(); 
		int T = 0;
		System.out.println("Critical tasks are:");
		for (Task task : tasks)	{
			task.delay();
			T = doProject();
			if (T > mMinTime) {
				System.out.print  ("Task : "+task.getName());
				System.out.println("\tID : "+task.getID());
			}
		}	
	}

	public Task[]	getTasks(){
		int N = getCapacity();
		Node[]	nodes = getNodes();
		Task[] 	tasks = new Task[N];
		for (int i = 0; i < N; i++) {
			tasks[i] = (Task) nodes[i];
		}
		return tasks;
	}

	public void handleFile(String fileName){
		// Read file line by line
		BufferedReader rdr = FileIO.getReader(fileName);	
		String 	line = "";
		String 	variables[];
		try{
			line = rdr.readLine();
			while (line != null){
				variables = line.split("\\s+");
				// Give each line into a method to make
				// the graph
				parseLine(variables);
				line = rdr.readLine();
			}
		}
		catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		}
		// The node edges need to be reversed because of how the file
		// is made up
		reverseGraph();
	}

	public void parseLine(String[] line){
		// Ignore empty strings
		if("".equals(line[0])) return;
		int 	N = line.length;
		// The first line decides the number of nodes
		// and only contains one value
		if(N == 1) {
			// Initialize a graph with a given capacity
			int taskCnt = Integer.parseInt(line[0]);
			//mGraph = new Graph(taskCnt);
			mCapacity	= taskCnt;
			mNodes 		= new Node[mCapacity];
			mAdjacencyList 	= new int [mCapacity][];
			return;
		}	
		// We are now parsing nodes
		int	number;
		String 	name;
		int 	T;
		int	M;

		number = Integer.parseInt(line[0]);
		name   = 		  line[1];
		T      = Integer.parseInt(line[2]);
		M      = Integer.parseInt(line[3]);
		// Now there are only node links left
		int [] 	adjacentTasks = new int[N-4];
		for (int i = 4; i < N-1; i++) {
			adjacentTasks[i-4] =  Integer.parseInt(line[i]);
		}
		
		// Create Task
		Task newTask = new Task(name, number, T, M, adjacentTasks);
		addNode(newTask);
	}
}
