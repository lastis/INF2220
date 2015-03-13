package graph;

public class Node {
	int		mID;
	int	[]	mAdjacencies;
	int		mWeight = 1;
	int		mOutEdges;
	int		mInEdges;

	public Node(int ID, int adjacencies[]){
		mID    	     = ID;
		mAdjacencies = adjacencies;
		mOutEdges    = adjacencies.length - 1;
	}

	public void	printNabors(){
		System.out.print("Nabors to "+mID+" is:");
		for (int i : mAdjacencies){
			System.out.print(" ");
			System.out.print(i);
		}	
		System.out.println("");
	}

	public 	void	increaseInEdges(){
		mInEdges++;
	}

	public 	int 	getID(){
		return mID;
	}

	public	void	setAdjacencies(int[] adjacencies){
		mAdjacencies = adjacencies;
	}

	public 	int[] 	getAdjacencies(){
		return mAdjacencies;
	}

	/**
	 * @return the mOutEdges
	 */
	public int getOutEdges() {
		return mOutEdges;
	}

	/**
	 * @return the mInEdges
	 */
	public int getInEdges() {
		return mInEdges;
	}

}
