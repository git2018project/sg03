package project3;

/**
 * 
 * 
 *
 */
public class CarNode{
	
	private Car car; // this car
	private CarNode children;
	private CarNode sibling;
	private char value;

	public CarNode(char val){
		value = val;
	}

	public Car getCar(){
		return car;
	}

	public CarNode getChildren(){
		return children;
	}

	public CarNode getSibling(){
		return sibling;
	}

	public char getVal(){
		return value;
	}

	public void setCar(Car newCar){
		car = newCar;
	}

	public void setChildren(CarNode children){
		this.children = children;
	}

	public void setSibling(CarNode sibling){
		this.sibling = sibling;
	}

	public void setVal(char val){
		value = val;
	}
}