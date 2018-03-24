package project3;
/**
 * @author Shushma Gudla
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class CarTracker{
	
    private static DLBBasedOnVIN cars; // DLB Trie for cars that are created for faster access.
    private static CarPQManager heapManager; // Manages all of the heaps for all prices,  mileages, and the same for all makes and models
    private static Scanner scanner; //Scanner for input 

    public static void main(String[] args){
    	
        scanner = new Scanner(System.in);
        cars = new DLBBasedOnVIN();
        heapManager = new CarPQManager();
        String input = "";
        try{ 
	        readCarsFromFile(); // load list of cars from the cars.txt file.
	
	        do{
	           
	            System.out.println("\t 1) Add a car");
	            System.out.println("\t 2) Update a car");
	            System.out.println("\t 3) Remove a car");
	            System.out.println("\t 4) Retrieve the lowest priced car");
	            System.out.println("\t 5) Retrieve the lowest mileage car");
	            System.out.println("\t 6) Retrieve the lowest priced car by make or model");
	            System.out.println("\t 7) Retrieve the lowest mileage car by make or model");
	            System.out.println("\t 8) Quit");
	            System.out.print("Choose a number: ");
	            input = scanner.nextLine();
	
	            if(input.equals("1")){
	                addCar();
	            } else if(input.equals("2")){
	                updateCar();
	            } else if(input.equals("3")){
	                removeCar();
	            } else if(input.equals("4")){
	                getLowestPriceCar();
	            } else if(input.equals("5")){
	                getLowestMileageCar();
	            } else if(input.equals("6")){
	                getLowestPriceCarByMakeAndModel();
	            } else if(input.equals("7")){
	                getLowestMileageCarByMakeAndModel();
	            } else if(input.equals("8")){
	                System.exit(0);
	            } else{
	                System.out.println("Invalid choice!\n");
	            }
	        } while(!input.equals("8"));
        }  catch (IOException e)
        {
            System.out.println("cars.txt File not found");
            
        } 
    }

    //A user adds a car with its necessary information
    public static void addCar(){
    	
        String vin = "";
        String make = "";
        String model = "";
        String color = "";
        int mileage = 0;
        int price = 0;

        System.out.print("\nEnter a VIN: ");
        vin = scanner.nextLine();
        
        if(cars.isVINExists(vin)){  //Checks to see if the car already exists; if so, don't add it
            System.out.println("\n--- A car with that VIN number already exists! ---");
            System.out.println("\n");
            
            return;
        }

        System.out.print("Enter a Make: ");
        make = scanner.nextLine();

        System.out.print("Enter a Model: ");
        model = scanner.nextLine();

        System.out.print("Enter a Color: ");
        color = scanner.nextLine();
       
        do{
        	System.out.print("Enter a valid Mileage:");        	
        	mileage = parseInt(scanner.nextLine());
          
        } while( mileage < 1);
        
        do{
        	System.out.print("Enter a Price (whole USD): $");
            price = Integer.parseInt(scanner.nextLine());
          
        } while( price < 1);      
       // create a car
        Car newCar = new Car(vin, make, model, color, mileage, price);

        cars.insert(newCar); //Insert the car into the symbol table
        heapManager.insert(newCar); //Insert the car into the appropriate all four heaps 
        }

    //Update a car's price, mileage, or color
    public static void updateCar() {
    	
        System.out.print("\nEnter the VIN of the car to update ('q' or 'Q' to Quit): ");
        
        String vin = scanner.nextLine();
        if(vin.equalsIgnoreCase("q")) return; 

        Car car = cars.getCar(vin); //Grab the car from the symbol table in constant time
        while(car == null){ //Car doesn't exist
            System.out.println("\n=== Invalid VIN ===");
            System.out.print("Enter the VIN of the car to update ('q' or 'Q' to Quit): "); //Prompt the user to enter a new VIN or quit
            vin = scanner.nextLine();
            if(vin.equalsIgnoreCase("q")) return;
            car = cars.getCar(vin); //The user entered another VIN
        }

        String choice = getPriceMileaeColorChoice();

        //Update the price of the car
        if(choice.equals("1")){
            int price = -1;

            do{
            	System.out.print("Enter a Price (whole USD): $");
                price = Integer.parseInt(scanner.nextLine());
              
            } while( price < 1);   

            //Change the property of the car
            car.setPrice(price);
            heapManager.updatePrice(car); 
            
        } else if(choice.equals("2")){ //Update the mileage of the car
            int mileage = -1;

            do{
            	System.out.print("Enter a valid Mileage:");        	
            	mileage = parseInt(scanner.nextLine());
              
            } while( mileage < 1);            

            //Change the property of the car
            car.setMileage(mileage);
            heapManager.updateMileage(car); 
            
            
        } else if(choice.equals("3")){
            System.out.print("Enter a color: ");                    
            car.setColor( scanner.nextLine());
        } else if(choice.equalsIgnoreCase("q")){
            return;
        }
    }

	private static String getPriceMileaeColorChoice() {
		//prompt the user 1:  Price 2 : Mileage 3 : Color
        System.out.println("\nWould you like to update (enter number, 'q' or 'Q' to Quit):\n\t 1) Price\n\t 2) Mileage\n\t 3) Color");
        System.out.print("Choose one: ");
        String choice = scanner.nextLine();
        while(!choice.equals("1") && !choice.equals("2") && !choice.equals("3") && !choice.equalsIgnoreCase("q")){
            System.out.println("\n=== Invalid Choice! ===");
            System.out.println("Would you like to update (enter number, q' or 'Q' to Qui):\n\t1) Price\n\t2) Mileage\n\t3) Color");
            System.out.print("Choose one: ");
            choice = scanner.nextLine();
        }
		return choice;
	}
// Remove the car from Trie    
    public static void removeCar(){
    	
        System.out.print("\nEnter the VIN of the car to remove ('q or Q' to Quit)): ");
        String vin = scanner.nextLine();
        if(vin.equalsIgnoreCase("Q")) return;

        Car car = cars.getCar(vin); //Grab the car from the symbol table
        while(car == null){ //Car doesn't exist
            System.out.println("=== Invalid Car! ===");
            System.out.print("\nEnter the VIN of the car to remove ('q' to Quit): "); //Prompt the user to enter another VIN or quit
            vin = scanner.nextLine();
            if(vin.equalsIgnoreCase("Q")) return;
            car = cars.getCar(vin); //The user entered another VIN, so grab that car
        }

        heapManager.remove(car); //Remove the car from the necessary priority queues
        cars.remove(vin); //Remove the car from the symbol table
    }

   
    public static void getLowestPriceCar(){
        Car lowest = heapManager.getLowestPrice();

        if(lowest == null) System.out.println("\n--- No cars available. ---"); //No cars exist
        else System.out.println("\n" + lowest.toString()); //Print the car for the user
    }

  
    public static void getLowestMileageCar(){
        Car lowest = heapManager.getLowestMileage();

        if(lowest == null) System.out.println("\n--- No cars available. ---"); //No cars exist
        else System.out.println("\n" + lowest.toString()); //Print the car for the user
    }

    //Retrieve the lowest priced car from a specific make and model
    public static void getLowestPriceCarByMakeAndModel(){
        System.out.print("\nEnter a make (Honda,Toyota, Ford, Kia, ...): ");
        String make = scanner.nextLine();

        System.out.print("Enter a model (Accord, Camry, Fiesta, Civic, ...): ");
        String model = scanner.nextLine();

        Car lowest = heapManager.getLowestMMPrice(make, model); //Get Lowest Make Model Price

        if(lowest == null) {
        	System.out.println("\n--- No car available for that make and model. ---"); //No car exist for this make and model
        }
        else System.out.println("\n" + lowest.toString()); //Print the car for the user
    }

    //Retrieve the lowest mileage car from a specific make and model
    
    public static void getLowestMileageCarByMakeAndModel(){
    	
        System.out.print("\nEnter a make (Honda,Toyota, Ford, Kia, ...): ");
        String make = scanner.nextLine();

        System.out.print("Enter a model (Accord, Camry, Fiesta, Civic, ...): ");
        String model = scanner.nextLine();

        Car lowest = heapManager.getLowestMMMileage(make, model); //Get Lowest Make Model Mileage

        if(lowest == null){
        	System.out.println("\n--- No car available for that make and model. ---");
        }
        
        else System.out.println("\n" + lowest.toString()); //Print the car for the user
    }

	public static int parseInt(String value) {
		int number = 0;
		try {

			number = Integer.parseInt(value);

		} catch (NumberFormatException e) {

		}
		return number;
	}

	public static void readCarsFromFile() throws IOException {

		FileReader reader = new FileReader("cars.txt");
		BufferedReader bufferedReader = new BufferedReader(reader);
		String line;
		int lineNumber = 1;
		while ((line = bufferedReader.readLine()) != null) {
			if (lineNumber != 1) {
				
				String[] values = line.split(":"); // parse input line with delimeter :

				Car newCar = new Car(values[0], values[1], values[2], values[5], Integer.parseInt(values[4]),
						Integer.parseInt(values[3]));

				cars.insert(newCar); 
				heapManager.insert(newCar);
											

			}
			lineNumber++;
		}

	}
         
    
}