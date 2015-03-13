package init;
import	graph.*;

public class Task extends Node{
	String 	mName;
	int 	mTime, mStaff;
	int	mEarliestStart;
	int	mEarliestEnd;
	int 	mDonePredecessors;
	int	mDelay = 0;
	boolean mDone = false;

	Task(String name, int ID, int time, int	staff, int adjacencies[]){
		// A basic node only contains a ID and adjacent nodes
		super(ID, adjacencies);
		mName  = name;
		mTime  = time;
		mStaff = staff;
	}

	public	String	getName(){
		return	mName;
	}

	public	void	delay(){
		mDelay++;
	}

	public void	reset(){
		mEarliestStart = 0;
		mEarliestEnd = 0;
		mDonePredecessors = 0;
		mDone = false;
		mDelay = 0;
	}

	public void 	print(){
		System.out.println("Task: "+mName+"\tID: "+getID());
	}
	
	public	boolean	isDone(){
		return mDone;
	}

	public void 	continueTask(int time, Graph graph){
		// This is the method that will be called many times
		// from the project object
		if (time == mEarliestEnd) finishTask(time, graph);  
	}

	public boolean	suggestStart(int time, Graph graph){
		// This method is only called by completed tasks 
		// (and once for initiating the graph traversion)
		// and those tasks also calls predecessorDone() on us. 
		// Will only be true one time (in theory)
		if(mDonePredecessors == getInEdges()){
			mEarliestStart  = time;
			mEarliestEnd	= time + mTime + mDelay;
			// Start this task, only happens here
			startTask(time, graph);
			return true;
		}
		return false;
	}

	public	void	predecessorDone(){
		// This method is only called by completed tasks 
		mDonePredecessors++;
	}

	public 	void	startTask(int time, Graph graph){
		((Project)graph).taskStarted(mStaff, time);
		if(graph.shouldPrint()){
			System.out.print("Time : "+time+"\tStarting task:\t");
			System.out.println(mName);
		}
	}

	public 	void	finishTask(int time, Graph graph){
		((Project)graph).taskFinished(mStaff,time);
		if(graph.shouldPrint()){
			System.out.print("Time : "+time+"\tEnding task  :\t");
			System.out.println(mName);
		}
		// Toggle this task as done
		mDone = true;

		// This should only happen once, now we call
		// suggest start on alle nodes after this one
		for (int ID : getAdjacencies()) {
			if (ID == 0) break;
			Task task = (Task) graph.getNode(ID);
			task.predecessorDone();
			task.suggestStart(time, graph);
		}	
	}
}

