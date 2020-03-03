package ukp;

/* A class which computes the maximum worth of a knapsack given a list of options to put in it, with their
 * weights and values, 
 */
import java.util.Vector;

public class UKPDynamic{
	
	//data structures
	private int capacity;
	private Vector<String>  names;
	private Vector<Integer> weights;
	private Vector<Integer>  values;
	private int maxValue;

	//Constructor. Assign given data structures to this object, and perform the computation.
	public UKPDynamic(int capacity, Vector<String> names, Vector<Integer> weights, Vector<Integer> values){
		this.capacity = capacity;
		this.names = names;
		this.weights = weights;
		this.values = values;
		this.maxValue = unboundedKnapSack(capacity, names, weights, values);
	}
	
	//return value of full capacity
	public int getMax(){
		return maxValue;
	}

	private int unboundedKnapSack(int capacity, Vector<String> names, Vector<Integer> weights, Vector<Integer> values){
		//where we will dynamically build up the entries to the array
		int[] maxValueAtWeight = new int[capacity + 1];

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
					maxValueAtWeight[i] = max(maxValueAtWeight[i],
					maxValueAtWeight[i - weights.get(j)] + values.get(j));
				}
			}
		}
		return maxValueAtWeight[capacity];
	}

	//max of two ints, for readability
	public static int max(int n1, int n2){
		return (n1 < n2)? n2 : n1;
	}
}
