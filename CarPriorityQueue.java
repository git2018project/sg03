package project3;

//CarPriorityQueue.java is a priority queue develped with a min-heap as its underlying data structure.  



public class CarPriorityQueue{
    private int size;           //number of cars.
    private Car[] heap;      // will be set t0 initial size to be 2^9
    private char mode;       //'m' = mileages, 'p' = prices
    private boolean makeModelHeapFlag; //false = a heap for all makes/models; true = min heap for specific make/models, 


    public CarPriorityQueue(char mode, boolean makeModelHeapFlag) {
        size = 0;  // set to zero.
        this.mode = mode;
        this.makeModelHeapFlag = makeModelHeapFlag;
        heap = new Car[511]; // set initial size to be 2^9
    }

    
    public int getSize() {
        return size;
    }

    //Insert a car into the PQ
    public void insert(Car key){
        if(key == null) return; //Can't insert a null Car
        size++; //increment by one
        if(size >= heap.length) //Re-adjust the heap size
        { 
            doubleHeapSize();
        }
        heap[size] = key; //Insert the car
        swim(size); //Place it at the correct position in the heap based on its appropriate property value (mileage or price)
    }
    
    //Change the car property associated with index i to the specified value and update its position in the heap
    public void update(int i){
        if (i < 0) { 
        	throw new IndexOutOfBoundsException(); // invalid number.
        }
        swim(i); //When the Car is updated at index i, swim to the top of the heap
        sink(i); //and then sink it to its appropriate location
    }

    //Remove the car associated with index i
    public void delete(int i){
        if (i < 0){
        	throw new IndexOutOfBoundsException();
        }
        swap(i, size--); //Place the car at the bottom of the heap
        swim(i); //Swim the swapped in car to the top
        sink(i); //and then sink it down to its appropriate location
        heap[size+1] = null; //Make sure the car we deleted is set to null
    }
    

    //Doubles the size of the heap once it's full
    public void doubleHeapSize(){
        Car[] newHeap = new Car[size*2]; //Double the size of the heap
        for(int i = 0; i <= size; i++){ //Copy all the items from the old heap over to the new heap
            newHeap[i] = heap[i];
        }
    }

    //Returns the Car with the minimum value (price or mileage) in the heap
    public Car getMin(){
        if (size == 0) return null;
        return heap[1];
    }

   
    
    
    


// General helper functions.

    private boolean greater(int i, int j){
        if(mode == 'm') {
        	return heap[i].getMileage() > heap[j].getMileage(); //Compare mileages of two cars
        }
        else if(mode == 'p') {
        	return heap[i].getPrice() > heap[j].getPrice(); //Compare prices of two cars
        }
        else return false; //return false
    }

    private void swap(int i, int j){
        Car swap = heap[i];
        heap[i] = heap[j];
        heap[j] = swap;

        //Allows the cars to be indexable in this current heap, so update the index variables for these cars
        setNewCarHeapIndex(i);
        setNewCarHeapIndex(j);
    }

    private void setNewCarHeapIndex(int j){
        if(mode == 'm'){ //mileage 
            if(makeModelHeapFlag) {
            	heap[j].setMakeModelMileageHeapIndex(j); //Set the index for the heap for this make/model
            }
            else heap[j].setMileageIndex(j); //Set the index for the heap for ALL cars
        } else if(mode == 'p'){ //price
            if(makeModelHeapFlag) heap[j].setMakeModelPriceHeapIndex(j); //Set the index for the heap for this make/model
            else heap[j].setPriceIndex(j); //Set the index for the heap for ALL cars
        }
    }  
   
    private void swim(int k){
        while (k > 1 && greater(k/2, k)){
            swap(k, k/2);
            k = k/2;
        }

        setNewCarHeapIndex(k);
    }

    private void sink(int k){
        while (2*k <= size){
            int j = 2*k;
            if (j < size && greater(j, j+1)) j++;
            if (!greater(k, j)) break;
            swap(k, j);
            k = j;
        }

        setNewCarHeapIndex(k);
    }
}

