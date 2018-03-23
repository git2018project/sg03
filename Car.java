package project3;

public class Car{
	
    private String color;
    private String make ;
    private String model ;
    private int price ;    
    private int mileage ;
   
    private String VIN = ""; // Vehicle Identification Number    
    
    
    private int pricesHeapIndex = -1; //Indexing for priority queue with ALL prices
    private int mileageHeapIndex = -1; //Indexing for priority queue with ALL mileages
   
    private int makeModelMileageHeapIndex = -1; //Indexing for the priority queue with this car's specific make/model/mileages
    private int makeModelPriceHeapIndex = -1; //Indexing for the priority queue with this car's specific make/model/prices   

    public Car(String VIN, String make, String model, String color, int mileage, int price){
        this.VIN = VIN;
        this.make = make;
        this.model = model;
        this.color = color;
        this.mileage = mileage;
        this.price = price;
    }

    public String getColor(){
        return  color;
    }

    public String getMake(){
        return make;
    }

    public int getMakeModelMileageHeapIndex(){
        return makeModelMileageHeapIndex;
    }

    public int getMileage(){
        return mileage;
    }

    public int getMileageIndex(){
        return mileageHeapIndex;
    }

    public int getMMPriceIndex(){
        return makeModelPriceHeapIndex;
    }

    public String getModel(){
        return model;
    }

    public int getPrice(){
        return price;
    }

    public int getPricesIndex(){
        return pricesHeapIndex;
    }

    public String getVIN(){
        return VIN;
    }

    public void setColor(String color){
        this.color = color;
    }

    public void setMakeModelMileageHeapIndex(int index){
        makeModelMileageHeapIndex = index;
    }

    public void setMakeModelPriceHeapIndex(int makeModelPriceHeapIndex){
        this.makeModelPriceHeapIndex = makeModelPriceHeapIndex;
    }

    public void setMileage(int mileage){
        this.mileage = mileage;
    }

    public void setMileageIndex(int mileageHeapIndex){
        this.mileageHeapIndex = mileageHeapIndex;
    }

    public void setPrice(int price){
        this.price = price;
    }

    public void setPriceIndex(int pricesHeapIndex){
        this.pricesHeapIndex = pricesHeapIndex;
    }
/**
 * override toString method
 */
    public String toString(){
        return "\n\tMake: " + make +
        		"\n\tModel: " + model + 
        		"\n\tColor: " + color + 
        		"\n\tMileage: " + mileage + 
        		"\n\tPrice: $" + price;        
    }
}