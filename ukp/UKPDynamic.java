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
		this.capacity = factorize(capacity, weights);
		this.maxValue = unboundedKnapSack(this.capacity, this.names, this.weights, this.values);	//value of optimally filled knapsack, for all weights
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
					
					//if we should update current weight, store the element we used to get this more optimal value
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
	
	//a method which takes an array storing the last element added to the rucksack at each index[i] of the
	//knapsack which is optimally filled for all weights [i].
	private void updateFrequencies(int[] elementsAdded){
		
		//we need to traverse this backwards to count up all the elements used in
		//the "trail" that leads us to the optimally filled backpack at max weight
		int i = elementsAdded.length - 1;
		int element = elementsAdded[i];
		while (element != -1){
			numUses[element] += 1;
			
			//the next element of the current "trail" is
			//located at the current elements amount
			//of weight units earlier in the rucksack.
			i = i - weights.get(element);
			element = elementsAdded[i];
		}
	}
	
	//find the greatest commond denominator of the total capacity of the knapsack and
	//the weights of each individual element. Improves amortized runtime since this problem is
	//O(W*n) where W = weight/capacity of knapsack, and n is number of elements.
	private int factorize(int capacity, ArrayList<Integer> weights){
		int gcd = capacity;
		for (int i = 0; i < weights.size(); ++i){
			gcd = findGCD(min(weights.get(i), gcd), max(weights.get(i), gcd));
			if (gcd == 1){
				return capacity;
			}
		}

		//divide each number in arraylist by the gcd, return capacity divided by gcd
		for (int i = 0; i < weights.size(); ++i){
			weights.set(i, weights.get(i)/gcd);
		}
		return capacity / gcd;
	}
	
	//helper functions
	private int max(int a, int b){
		return (a < b) ? b : a;
	}

	private int min(int a, int b){
		return (a < b) ? a : b;
	}

	//euclids algorithm
	private int findGCD(int a, int b){
		if (a == 0){
			return b;
		}
		else{
			return findGCD(b % a, a);
		}
	}

}
