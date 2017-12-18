import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MatrixMultOrder {
	public static class Matrix {
		long m, n; // m and n dimensions of matrix

		public Matrix(long m, long n){
			this.m = m;
			this.n = n;
		}

		@Override 
		public boolean equals(Object obj) {
			if(!(obj instanceof Matrix))
				return false;
			Matrix mat = (Matrix) obj;
			return this.m == mat.m && this.n == mat.n;
		}

		@Override
		public int hashCode() {
			return (int)(m + 256 * n);
		}

		@Override
		public String toString() {
			return "(" + m + "," + n + ")";
		}
	}

	public static long minMultSteps(List<Matrix> ms){
		return memoizedMultStepsBottomUp(ms);
	}

	private static long memoizedMultStepsBottomUp(List<Matrix> ms) {
		Map<Integer, List<Long>> cost = new HashMap<>(); // map key is start, position in list is length-1
		for(int i = 0; i < ms.size(); i++){
			if(i < ms.size() - 1 && ms.get(i).n != ms.get(i+1).m)
				throw new IllegalArgumentException(ms.get(i) + " and " + ms.get(i+1) + " have invalid dims");
			List<Long> l = new ArrayList<>(ms.size() - i);
			l.add(0L);
			cost.put(i, l);
		}
		
		for(int l = 2; l <= ms.size(); l++) // chain length
			for(int i = 0; i <= ms.size() - l; i++){ // start
				Long nextCost = Long.MAX_VALUE;
				for(int k = i + 1; k < i + l; k++){ // pivot (part of the right parenthesis-ed exp)
					nextCost = Math.min(nextCost,
							cost.get(i).get(k - i - 1) + // left exp
							cost.get(k).get(i + l - k - 1) + // right exp
							ms.get(k).m * ms.get(i).m * ms.get(i + l - 1).n);
				}
				cost.get(i).add(nextCost);
			}
		
		return cost.get(0).get(ms.size() - 1);
	}

	private static long memoizedMultStepsTopDown(List<Matrix> ms) {
		Map<List<Matrix>, Long> memo = new HashMap<>();

		if(memo.containsKey(ms))
			return memo.get(ms);

		long ret = Long.MAX_VALUE;

		if(ms.size() == 1)
			return 0;
		else if(ms.size() == 2)
			ret = ((long)ms.get(0).m) * ms.get(0).n * ms.get(1).n;
		else {
			for(int right = 1; right < ms.size(); right++){
				if(ms.get(right).m != ms.get(right-1).n)
					throw new IllegalArgumentException("matrix " + 
							ms.get(right-1) + " and " + ms.get(right) + " mismatch.");
				long oneStepCost = ((long) ms.get(right-1).m) * ms.get(right).m *ms.get(right).n;
				List<Matrix> newMs = new ArrayList<>(ms);
				Matrix newM = new Matrix(newMs.get(right-1).m, newMs.remove(right).n);
				newMs.set(right-1, newM);
				long cost = oneStepCost + memoizedMultStepsTopDown(newMs);
				ret = Math.min(ret, cost);
			}

		}

		memo.put(ms, ret);
		return ret;
	}

	public static void main(String[] args){
		List<Matrix> list = new ArrayList<>();
		list.add(new Matrix(30, 35));
		list.add(new Matrix(35, 15));
		list.add(new Matrix(15, 5 ));
		list.add(new Matrix(5 , 10));
		list.add(new Matrix(10, 20));
		list.add(new Matrix(20, 25));
		// CLRS ex. up to here
		for(int i = 0; i < 100; i++){
		list.add(new Matrix(25, 35));
		list.add(new Matrix(35, 15));
		list.add(new Matrix(15, 5 ));
		list.add(new Matrix(5 , 10));
		list.add(new Matrix(10, 20));
		list.add(new Matrix(20, 25));		
		}
		
		for(int i = 2; i <= list.size(); i = (i + 100)/100 * 100){
			long start = System.currentTimeMillis();
			Long ans = minMultSteps(list.subList(0, i));
			System.out.println("List of size " + i + " took " + 
					(System.currentTimeMillis() - start)/1000. + " sec (" + ans + ")");
		}
	}
}
