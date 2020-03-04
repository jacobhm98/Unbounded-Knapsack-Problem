package ukp;

/* A class which computes the maximum worth of a knapsack given a list of options to put in it, with their
 * weights and values, 
 */
import java.util.ArrayList;

public class UKPDynamic{
	
	//data structures
	private int capacity;
	private ArrayList<String>  names;
	private ArrayList<Integer> weights;
	private ArrayList<Integer>  values;
	private int maxValue;
	private int[] numUses;

	//Constructor. Assign given data structures to this object, and perform the computation.
	public UKPDynamic(int capacity, ArrayList<String> names, ArrayList<Integer> weights, ArrayList<Integer> values){
		this.capacity = capacity;	//total impressions we can pump out this month (capacity of knapsack)
		this.names = names;		//names of customers
		this.weights = weights;		//number of impressions per order for each customer (same index)
		this.values = values;		//value of each order, what we look to maximize
		this.numUses = new int[names.size()]; //array where we will put the number of times each element is used
		this.maxValue = unboundedKnapSack(capacity, names, weights, values);	//value of optimally filled knapsack, for all weights
	}
	
	//return value of entire knapsack
	public int getMax(){
		return maxValue;
	}

	public int[] getNumUses(){
		return numUses;
	}

	private int unboundedKnapSack(int capacity, ArrayList<String> names, ArrayList<Integer> weights, ArrayList<Integer> values){
		//where we will dynamically build up the entries to the array
		int[] maxValueAtWeight = new int[capacity + 1];
		int[] elementsAdded = new int[capacity + 1];
		for (int i = 0; i < maxValueAtWeight.length; ++i){
			maxValueAtWeight[i] = 0;
			elementsAdded[i] = -1;
		}

		//iterate over all the weights
		for (int i = 0; i <= capacity; ++i){
			
			//for all the weights, iterate over all possible items and
			//check if its optimal to add current item to knapsack
			for (int j = 0; j < names.size(); ++j){
			
				//so we don't index with "negative weights", array out of bounds
				if(weights.get(j) <= i){
					
					//If we would get a better totValue by adding value of currElem to
					//optimal value at currWeight - weight of currElem, we know that
					//we should update the total value at this point.
					int currentBest = maxValueAtWeight[i];
					int potentialBetter = maxValueAtWeight[i - weights.get(j)] + values.get(j);

					if (potentialBetter > currentBest){
						maxValueAtWeight[i] = potentialBetter;
						elementsAdded[i] = j;
					}
				}
			}
		}
		updateFrequencies(elementsAdded);
		return maxValueAtWeight[capacity];
	}

	private void updateFrequencies(int[] elementsAdded){
		int i = elementsAdded.length - 1;
		int element = elementsAdded[i];
		while (element != -1){
			numUses[element] += 1;
			i = i - weights.get(element);
			element = elementsAdded[i];
		}
	}

}
