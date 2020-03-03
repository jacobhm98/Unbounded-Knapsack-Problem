package ukp;
import java.util.Vector;

public class UKPDynamic{
	int capacity;
	Vector<String>  names;
	Vector<Integer> weights;
	Vector<Integer>  values;
	int maxValue;

	public UKPDynamic(int capacity, Vector<String> names, Vector<Integer> weights, Vector<Integer> values){
		this.capacity = capacity;
		this.names = names;
		this.weights = weights;
		this.values = values;
		this.maxValue = unboundedKnapSack(capacity, names, weights, values);
	}

	public int getMax(){
		return maxValue;
	}

	private int unboundedKnapSack(int capacity, Vector<String> names, Vector<Integer> weights, Vector<Integer> values){
		//where we will dynamically build up the entries to the array
		int[] maxValueAtWeight = new int[capacity + 1];
		
		for (int i = 0; i <= capacity; ++i){
			for (int j = 0; j < names.size(); ++j){
				//so we don't index with "negative weights", array out of bounds
				if(weights.get(j) <= i){
					maxValueAtWeight[i] = max(maxValueAtWeight[i], maxValueAtWeight[i - weights.get(j)] + values.get(j));
				}	
			}
		}
		return maxValueAtWeight[capacity];
	}
	public static int max(int n1, int n2){
		return (n1 < n2)? n2 : n1;
	}
}
