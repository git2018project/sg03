package project3;

// CarPQManager allows the user to add, remove, update price and update mileage.

public class CarPQManager{
    private static CarPriorityQueue prices; 
    private static CarPriorityQueue mileages; 
    private static PriorQueueDLB makeModelPrices; //DLB Trie that stores priority queues for all makes. models and prices
    private static PriorQueueDLB makeModelMileages; //make, model and mileages
    private static String COMBINE ="&";

    public CarPQManager(){
    	mileages = new CarPriorityQueue('m', false); //false flag for for ALL cars
        prices = new CarPriorityQueue('p', false); //false flag  for ALL cars
      
        makeModelPrices = new PriorQueueDLB();
        makeModelMileages = new PriorQueueDLB();

    }

    public void insert(Car car){
        String make = car.getMake();
        String model = car.getModel();
        String makeModelString = make + COMBINE + model;  // Make unique combination..

        prices.insert(car);
        mileages.insert(car);
        CarPriorityQueue mmprices = makeModelPrices.getPriorityQueue(makeModelString); //prices heap for that specific make and model
        CarPriorityQueue mmmileages = makeModelMileages.getPriorityQueue(makeModelString); //mileages heap for that specific make and model
        if(mmprices == null){ //Make/Model prices heap doesn't exist
            CarPriorityQueue newPQ = new CarPriorityQueue('p', true); //true indicates it's for a specific make/model
            makeModelPrices.insert(makeModelString, newPQ); //Insert this PQ into the priority queue DLB
            mmprices = newPQ;
        }
        if(mmmileages == null){ //Make/Model mileages heap doesn't exist
            CarPriorityQueue newPQ = new CarPriorityQueue('m', true); //true indicates it's for a specific make/model
            makeModelMileages.insert(makeModelString, newPQ); //Insert this PQ into the priority queue DLB
            mmmileages = newPQ;
        }

        //Insert the car into its corresponding make/model priority queues
        mmprices.insert(car);
        mmmileages.insert(car);
    }

    //Get the car with the lowest price
    public Car getLowestPrice(){
        return prices.getMin();
    }

    //Get the car with the lowest mileage
    public Car getLowestMileage(){
        return mileages.getMin();
    }

    //Get the car with the lowest price for a specific make/model
    public Car getLowestMMPrice(String make, String model){
        String makeModelString = make + COMBINE + model;
        CarPriorityQueue mmprices = makeModelPrices.getPriorityQueue(makeModelString);

        return mmprices != null ? mmprices.getMin() : null; //If the PQ is null, it doesn't exist, so return null; otherwise, return the min
    }

    //Get the car with the lowest mileage for a specific make/model
    public Car getLowestMMMileage(String make, String model){
        String makeModelString = make + COMBINE+ model;
        CarPriorityQueue mmmileages = makeModelMileages.getPriorityQueue(makeModelString);

        return mmmileages != null ? mmmileages.getMin() : null; //If the PQ is null, it doesn't exist, so return null; otherwise, return the min
    }

    //Remove a car from its respective priority queues
    public void remove(Car car){
       
        int pricesIndex = car.getPricesIndex();
        int mileagesIndex = car.getMileageIndex();
        int mmpricesIndex = car.getMMPriceIndex();
        int mmmileagesIndex = car.getMakeModelMileageHeapIndex();
        String make = car.getMake();
        String model = car.getModel();

        prices.delete(pricesIndex);
        mileages.delete(mileagesIndex);

        String makeModelString = make + COMBINE + model;
        CarPriorityQueue mmprices = makeModelPrices.getPriorityQueue(makeModelString); //Grab the prices heap for this make/model from the DLB
        CarPriorityQueue mmmileages = makeModelMileages.getPriorityQueue(makeModelString); //Grab the prices heap for this make/model from the DLB
        if(mmprices != null) mmprices.delete(mmpricesIndex); //Make sure the heap exists first, then delete
        if(mmmileages != null) mmmileages.delete(mmmileagesIndex); //Make sure the heap exists first, then delete
    }

    //Update a car's mileage or price in its heaps
    public void updateMileage(Car car){ 
        String make = car.getMake();
        String model = car.getModel();
        String makeModelString = make + COMBINE + model;
        
      //Updated Mileage, so only update the car's mileage heaps
        int mileageIndex = car.getMileageIndex();
        int mileageMMIndex = car.getMakeModelMileageHeapIndex();
        mileages.update(mileageIndex);
        
        CarPriorityQueue mmmileages = makeModelMileages.getPriorityQueue(makeModelString);
        if(mmmileages != null) mmmileages.update(mileageMMIndex); //Make sure the heap exists first, then update
        
    }
    
  //Update a car's mileage or price in its heaps
    public void updatePrice(Car car){ 
        String make = car.getMake();
        String model = car.getModel();
        String makeModelString = make + COMBINE + model;        
        int pricesIndex = car.getPricesIndex();
        int pricesMMIndex = car.getMMPriceIndex();
        prices.update(pricesIndex);
        CarPriorityQueue mmprices = makeModelPrices.getPriorityQueue(makeModelString);
        if(mmprices != null) mmprices.update(pricesMMIndex); //Make sure the heap exists first, then update           
        
    }
}