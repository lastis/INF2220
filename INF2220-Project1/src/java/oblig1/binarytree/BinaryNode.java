package oblig1.binarytree;

public class BinaryNode<E extends Comparable<E>>{
	private E pointer;
	private BinaryNode<E> left;
	private BinaryNode<E> right;
	private BinaryNode<E> parent;
	
	public boolean hasLeft(){
		return left != null;
	}
	
	public boolean hasRight(){
		return right != null;
	}
	
	public E getPointer(){
		return pointer;
	}
	
	public void setPointer(E e){
		pointer = e;
	}

	public void setParent(BinaryNode<E> node){
		parent = node;
	}

	public void setLeft(BinaryNode<E> node){
		left = node;
		left.setParent(this);
	}

	public void setRight(BinaryNode<E> node){
		right = node;
	}

	public BinaryNode<E> getParent(){
		return parent;
	}

	public BinaryNode<E> getLeft(){
		return left;
	}

	public BinaryNode<E> getRight(){
		return right;
	}

}
	
