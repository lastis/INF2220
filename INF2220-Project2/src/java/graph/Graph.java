package graph;

public class Graph {
	int 	COUNT = 0;
	String	loopPath;
	boolean	mPrint = true;
	protected int		mCapacity;
	protected int 		mAdjacencyList [][];
	protected Node[]	mNodes;
	
	protected Graph(){
		// It is important to make sure you set all the values
		// here that would be in a normal constructor
	}

	public Graph(int capacity){
		mCapacity	= capacity;
		mNodes 		= new Node[capacity];
		mAdjacencyList 	= new int [capacity][];
	}

	public 	int	getCapacity(){
		return mCapacity;
	}

	public	void 	printON(){
		mPrint	= true;
	}

	public 	void 	printOFF(){
		mPrint	= false;
	}

	public 	boolean shouldPrint(){
		return mPrint;
	}

	public 	void 	printNodeNabors(){
		Node[] nodes = getNodes();
		for (Node node : nodes){
			node.printNabors();
		}	
	}

	public 	void	readyGraph(){
		computeNodeEdges();
	}

	public	void	reverseGraph(){
		int[][] nAdjList = new int[mCapacity][];
		int[][]	mAdjList = mAdjacencyList;
		int	newLength = 0;
		int 	node	  = 0;
		int	tmp 	  = 0;
		for (int i = 0; i < mCapacity; i++) {
			newLength = 0;
			node = i+1;
			for (int j = 0; j < mCapacity; j++) {
				for (int k = 0; k < mAdjList[j].length; k++){
					// Check which nodes are pointing
					// at me
					if(node == mAdjList[j][k]){
						// Count them
						newLength++;
					}
				}	
			}

			// Make a new list with that length
			nAdjList[i] = new int[newLength];
			tmp = 0;

			for (int j = 0; j < mCapacity; j++) {
				for (int k = 0; k < mAdjList[j].length; k++){
					// Find these nodes again and this
					// time get their IDs
					if(node == mAdjList[j][k]){
						// Get the ID of the new 
						// and create the new list
						nAdjList[i][tmp] = j+1;
						tmp++;
					}
				}	
			}
			// Now insert the new list in the node we are
			// working on
			getNode(node).setAdjacencies(nAdjList[i]);
		}	
		// replace the adjacency list
		mAdjacencyList = nAdjList;
	}

	public 	void	computeNodeEdges(){
		// The nodes out edges are decided in the
		// node constructor
		Node node;
		for (int i = 0; i < mCapacity; i++) {
			for (int nabor : mAdjacencyList[i]) {
				if(nabor == 0) break;
				node = getNode(nabor);
				node.increaseInEdges();
			}	
		}	
		
	}

	public 	void 	addNode(Node node){
		int   ID  = node.getID();
		int[] adj = node.getAdjacencies();
		// Insert the node to the node list
		// and create an adjacency list (ID starts at 1 not 0)
		mNodes	      [ID-1] = node;
		// This would probably cost some time because we might
		// rearrange memory many times while making this array
		mAdjacencyList[ID-1] = adj;
	}

	public Node[]	getIndependentNodes(){
		int []	IDs = new int[mCapacity];
		int 	N = 0;
		// Get the IDs of the independent nodes
		for (Node node : mNodes) {
			if(node.getInEdges() == 0){
				IDs[N] = node.getID();
				N++;
			}
		}
		// Make an array and insert the independent nodes
		Node[] nodes = new Node[N];
		for (int i = 0; i < N; i++) {
			nodes[i] = getNode(IDs[i]);
		}	
		return nodes;
	}

	public Node	getNode(int ID){
		if(ID == 0) {
			System.out.println("No node with ID: "+ID);
			return null;
		}
		return mNodes[ID-1];
	}

	/**
	 * @return the nodes
	 */
	public Node[] getNodes() {
		return mNodes;
	}

	public String 	getLoopPath(){
		checkForLoops();
		return loopPath;
	}

	public boolean checkForLoops(){
		boolean hasLoop    = false;
		int 	totalNodes = mCapacity;
		int[]	nabors;
		// Reset loopPath
		loopPath = "";
		for (int node = 1; node <= totalNodes; node++) {
			nabors  = mAdjacencyList[node-1];
			// Enter "nodes" from 1 to the limit
			for (int nabor : nabors) {
				// Look for loops at every nabor 
				// for every node
				hasLoop 
				  = recurCheckForLoop(node,nabor);
				if(hasLoop) {
					loopPath += node;
					return true;
				}
			}
		}
		return false;
	}

	private boolean recurCheckForLoop(int 	 initalNode, 
				      	  int 	 thisNode){
		// The node integer represents to node ID and is always
		// assumed to be the index of the node (minus one) in the mNodes array
		// and the first array of mAdjacencyList

		// If thisNode == 0 it means we have reached the end of the
		// adjacency list and there are no more nabors
		if(thisNode == 0) return false;
		if(initalNode == thisNode){
			// A loop has been found, start creating
			// a loop path
			loopPath = thisNode + " ";
			return true;
		}
		// At this point there are more nabors unchecked
		// Check again for all nabors of this node
		boolean hasLoop = false;
		for (int nabor : mAdjacencyList[thisNode-1]) {
			hasLoop = recurCheckForLoop(initalNode,nabor);
			if(hasLoop){
				// We have found a loop, start creating a path
				// from the looping nodes
				loopPath = loopPath + thisNode + " ";
				System.out.println("Loop Path : ");
				System.out.println(loopPath);
				return true;
			}
		}
		return false;
	}
}
