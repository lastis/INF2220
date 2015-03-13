package oblig1.binarytree;

class NumberNode {
	private NumberNode next = null;
	private int num 	= 0;

	/**
	 * @return the next
	 */
	public NumberNode getNext() {
		return next;
	}

	/**
	 * @param next the next to set
	 */
	public void setNext(NumberNode next) {
		this.next = next;
	}

	/**
	 * @return the num
	 */
	public int getNum() {
		return num;
	}

	/**
	 * @param num the num to set
	 */
	public void setNum(int num) {
		this.num = num;
	}

	public void add(int num) {
		this.num += num;
	}

	public void subtract(int num) {
		this.num -= num;
	}

	public boolean hasNext() {
		if (next == null) 	return false;
		else 			return true;
	}
}
