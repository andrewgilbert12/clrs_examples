import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class LongestCommonSubsequence {
	public static void main(String[] args) throws FileNotFoundException{
		String a = "10010101";
		String b = "010110110";
		
		for(String c : lcs(Arrays.asList(a.split("")), Arrays.asList(b.split(""))))
			System.out.print(c);

	}
	
	public static <E> List<E> lcs(List<E> X, List<E> Y){
		Map<Integer, Map<Integer, List<E>>> memo = new HashMap<>();
		for(int x = 1; x <= X.size(); x++)
			memo.put(x, new HashMap<Integer, List<E>>());
		return lcs(X, Y, memo);
	}
	
	public static <E> List<E> lcs(List<E> X, List<E> Y, Map<Integer, Map<Integer, List<E>>> memo){
		int m = X.size(), n = Y.size();
		if(memo.containsKey(m) && memo.get(m).containsKey(n)){
			return memo.get(m).get(n);
		}
		
		if(m == 0 || n == 0)
			return new ArrayList<>(); // empty
		
		if(X.get(m - 1).equals(Y.get(n - 1))){
			List<E> ret = new ArrayList<>(lcs(X.subList(0, m - 1), Y.subList(0, n - 1), memo)); // copy
			ret.add(X.get(m - 1));
			memo.get(m).put(n, ret);
			return ret;
		}
		
		List<E> left = lcs(X.subList(0, m - 1), Y, memo);
		List<E> right = lcs(X, Y.subList(0, n - 1), memo);
		
		if(left.size() > right.size()){
			memo.get(m).put(n, left);
			return left;
		} else {
			memo.get(m).put(n, right);
			return right;
		}
	}
}
