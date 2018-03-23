package project3;

public class PriorityQueueNode{
	private PriorityQueueNode children;
	private CarPriorityQueue priorityQueue;
	private PriorityQueueNode sibling;
	private char value;

	public PriorityQueueNode(char val){
		value = val;
	}

	public PriorityQueueNode getChildren(){
		return children;
	}

	public CarPriorityQueue getPriorityQueue(){
		return priorityQueue;
	}

	public PriorityQueueNode getSibling(){
		return sibling;
	}

	public char getVal(){
		return value;
	}

	public void setChildren(PriorityQueueNode nextReference){
		children = nextReference;
	}

	public void setPriorityQueue(CarPriorityQueue newPQ){
		priorityQueue = newPQ;
	}

	public void setSibling(PriorityQueueNode nextReference){
		sibling = nextReference;
	}

	public void setVal(char val){
		value = val;
	}
}