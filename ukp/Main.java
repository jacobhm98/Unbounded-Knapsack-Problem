/* A class which reads in the data from a file given as the first argument formatted according to specifications.
 * Puts the data into suitable datastructures and passes these to UKPDynamic which performs a dynamic computation
 * of the Unbounded Knapsack Problem.
 */

package ukp;
import java.io.*;
import java.util.ArrayList;

public class Main{
	
	public static void main(String[] args){
		
		//Initialize data structures
		if (args.length < 1){
			System.out.println("provide input file");
			return;
		}	
		String inputFilePath = args[0];
		int capacity;
		ArrayList<String> names = new ArrayList<>();
		ArrayList<Integer> weights = new ArrayList<>();
		ArrayList<Integer> values = new ArrayList<>();
		
		//Read the file we got and update the initialized ds', as they are passed by
		//reference
		capacity = readData(inputFilePath, names, weights, values);
		
		//perform the computations on the data we received
		UKPDynamic ukp = new UKPDynamic(capacity, names, weights, values);
	}

	
	//A method which accepts a file location, and three vectors. Reads data from this file and stores it
	//in the desired datastructures. Returns the total capacity.
	public static int readData(String inputFilePath, ArrayList<String> names, ArrayList<Integer> weight, ArrayList<Integer> value){
		try{
			BufferedReader in = new BufferedReader(new FileReader(inputFilePath));
			
			//Find and store total number of impressions for the month
			int capacity = Integer.parseInt(in.readLine());
			
			//read in the rest of the data and store it where we want to
			String line;
			while((line = in.readLine()) != null){
				String[] data = line.split(",");
				names.add( data[0]);
				weight.add(Integer.parseInt(data[1]));
				value.add(Integer.parseInt(data[2]));
			}
			return capacity;
		}
		
		catch(FileNotFoundException e){
			System.err.println("provide an input file as argument 1!");
		}
		
		catch (IOException e){
			System.err.println("Input file not formatted correctly");
		}
		
		return -1;
	}

}
