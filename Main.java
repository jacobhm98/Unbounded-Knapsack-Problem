/*
 */

package unboundeKnapsack;
import java.io.*;
import java.util.Vector;

public class Main{
	
	public static void main(String[] args){
		String inputFilePath = args[0];
		int Capacity;
		Vector<String> names = new Vector<>();
		Vector<Integer> weights = new Vector<>();
		Vector<Integer> values = new Vector<>();
		Capacity = readData(inputFilePath, names, weights, values);
	}

	public static int readData(String inputFilePath,Vector<String> names, Vector<Integer> weight, Vector<Integer> value){
		try{
			BufferedReader in = new BufferedReader(new FileReader(inputFilePath));
			//Find and store total number of impressions for the month
			int Capacity = Integer.parseInt(in.readLine());
			//get rid of empty line
			in.readLine();
			//read in the rest of the data and store it where we want to
			String line;
			while((line = in.readLine()) != null){
				String[] data = line.split(",");
				names.add( data[0]);
				weight.add(Integer.parseInt(data[1]));
				value.add(Integer.parseInt(data[2]));
			}
			return Capacity;
		}
		catch(FileNotFoundException e){
			System.err.println("provide an input file as argument 1!");
		}
		catch (IOException e){
			System.err.println("Input file not formatted correctly");
		}
		return = -1;
	}
}
