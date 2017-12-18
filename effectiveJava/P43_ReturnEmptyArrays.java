package effectiveJava;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// it's good practice to return empty arrays rather than null or other "special cases"
// this way a for all loop that processes the result will simply never run, which is often
// all that we need to do for this type of edge case anyway.
public class P43_ReturnEmptyArrays {
	private static class Cheese {
		private String name;

		public Cheese(String name){
			this.name = name;
		}

		public String getName(){
			return name;
		}
	}

	public static void main(String[] args){

	}

	private static final Cheese[] EMPTY_CHEESE_ARRAY = new Cheese[0];
	private static List<Cheese> CheeseStock = new ArrayList<>();

	public static List<Cheese> cheeses(){
		return Collections.emptyList();
	}
}
