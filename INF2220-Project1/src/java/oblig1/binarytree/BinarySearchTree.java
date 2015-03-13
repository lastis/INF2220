package oblig1.binarytree;

public class BinarySearchTree<E extends Comparable<E>>{
	private BinaryNode<E> firstNode;
	private int size 	= 0;
	private int depth 	= 0;
	private boolean statReady = false;
	private NumberNode depthNode;

	public boolean add(E e) throws NullPointerException{
		if (e == null) throw new NullPointerException();
		BinaryNode<E> newNode = new BinaryNode<E>();
		newNode.setPointer(e);
		// Add the first node	
		if(firstNode == null) {
			firstNode = newNode;
		}
		// Add to tree
		BinaryNode<E> node = firstNode;
		while (node != null){
			int i = e.compareTo(node.getPointer());
			if(i < 0){
				if(!node.hasLeft()) {
					node.setLeft(newNode);	
					size++;
					statReady = false;
					return true;
				}
				else node = node.getLeft();
			}	
			else if(i > 0){
				if(!node.hasRight()){
					node.setRight(newNode);
					size++; 
					statReady = false;
					return true;
				}
				else node = node.getRight();
			}
			// the node has a duplicate in the list
			else return false;
		}
		return false;
	}


	public boolean contains(E e) {
		return get(e) != null;
	}

	public boolean remove(E e){
		// First find it, then remove it
		BinaryNode<E> target = getNode(e, firstNode);
		// If target == null, target is not in the list
		if (target == null) return false; 
		remove(target);
		statReady = false;
		return true;
	}

	private void remove(BinaryNode<E> target) {
		boolean left 	= target.hasLeft();
		boolean right 	= target.hasRight();
		if(left && right) {
			BinaryNode<E> successor = findMin(target.getRight());
			// Change keys
			target.setPointer(successor.getPointer());
			remove(successor);
		}
		else if (left) {
			ReplaceNodeInParent(target, target.getLeft());
		}
		else if (right) {
			ReplaceNodeInParent(target, target.getRight());
		}
		else {
			ReplaceNodeInParent(target, null);
		}
	}

	private void ReplaceNodeInParent(BinaryNode<E> node, BinaryNode<E> newNode) {
		BinaryNode<E> parent = node.getParent();
		if (node == parent.getLeft()) {
			parent.setLeft(newNode);
		}
		else if (node == parent.getRight()) {
			parent.setRight(newNode);
		}
	}

	public E findMin(){
		return findMin(firstNode).getPointer();
	}

	private BinaryNode<E> findMin(BinaryNode<E> startNode){
		BinaryNode<E> current = startNode;
		while (current.getLeft() != null){
			current = current.getLeft();
		}	
		return current;
	}

	public E findMax(){
		return findMax(firstNode).getPointer();
	}

	private BinaryNode<E> findMax(BinaryNode<E> startNode){
		BinaryNode<E> current = startNode;
		while (current.getRight() != null){
			current = current.getRight();
		}	
		return current;
	}

	public boolean isEmpty() {
		if(!statReady) 	return true;
		if(size == 0) 	return true;
		else 		return false;
	}

	public void clear() {
		size 		= 0;
		depth 		= 0;
		firstNode 	= null;
		depthNode	= null;
		statReady	= false;
	}

	public E get(E e){
		BinaryNode<E> node = getNode(e, firstNode);
		if(node == null)	return null;
		else 			return node.getPointer();	
	}

	private BinaryNode<E> getNode(E e, BinaryNode<E> startNode) {
		BinaryNode<E> node = startNode;
		if(node == null) return null;
		// Loop until we find the node
		int i = e.compareTo(node.getPointer());
		while(i != 0){
			if (i < 0) {
				node 	= node.getLeft();
			}
			else if (i > 0) {
				node	= node.getRight();
			}
			// Breake loop if we cannot find it
			if(node == null) return null;
			i = e.compareTo(node.getPointer());
		}
		return node;
	}

	public void printStatistics(){
		readyStatistics();
		String output;
		output  = "The binary tree statistics: \n";
		output += "Nodes:\t\t\t" + size + "\n";
		output += "Depth:\t\t\t" + depth + "\n";
		output += "Avrage depth\t\t" + size/depth + "\n";
		output += "Nodes at depths:\n";
		for (int i = 1; i < depth+1; i++){
			output += "Depth " + i + ":\t" + getNodesAtDepth(i-1);
			output += "\t";
			if(i%3 == 0) output += "\n";
		}	
		System.out.println(output);
	}

	public void readyStatistics(){
		size  = 0;
		depth = 0;
		depthNode = new NumberNode();
		recur(firstNode, depthNode);

		NumberNode node = depthNode;
		while(node.hasNext()) {
			depth++;
			node = node.getNext();
		}
		statReady = true;
	}

	public int getDepth(){
		if(!statReady) return 0;
		return depth;
	}

	public int getSize(){
		if(!statReady) return 0;
		return size;
	}

	public int getNodesAtDepth(int n) {
		if(!statReady) return 0;
		if(n > depth) return 0;
		NumberNode node = depthNode;
		for(int i = 0; i < n; i++) {
			node = node.getNext();
		}
		return node.getNum();
	}



	private void recur(BinaryNode<E> node, NumberNode depthNode){
		boolean left 	= node.hasLeft();
		boolean right 	= node.hasRight();
		NumberNode nextDepthNode = depthNode.getNext();

		// Add one to the size of the tree
		size++;
		// Add one node at this depth
		depthNode.add(1);
		if(left) {
			// Generate a new node in the node list
			// if we need to
			if(nextDepthNode == null) {
				nextDepthNode = new NumberNode();
				depthNode.setNext(nextDepthNode);
			}
			recur(node.getLeft(), nextDepthNode);
		}
		if(right) {
			if(nextDepthNode == null) {
				nextDepthNode = new NumberNode();
				depthNode.setNext(nextDepthNode);
			}
			recur(node.getRight(),  nextDepthNode);
		}
	}
}
