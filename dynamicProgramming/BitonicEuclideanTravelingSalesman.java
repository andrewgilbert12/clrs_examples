package dynamicProgramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class BitonicEuclideanTravelingSalesman {
	public static void main(String[] args){
		final int N = 10_000;
		final int X_RANGE = 10; // per step can go between 1 and this number inclusive
		final int Y_RANGE = 100; // Y value will be between 0 and this number inclusive
		List<Point> list = new ArrayList<>(N);
		Random r = new Random();
		int curr_x = 0;
		for(int n = 0; n < N; n++){
			Point pt = new Point(curr_x, r.nextInt(Y_RANGE + 1));
			list.add(pt);
			curr_x += 1; // r.nextInt(X_RANGE) + 1;
//			System.out.println(pt);
		}

		System.out.println(shortestBitonicLoop(list));
	}

	public static double shortestBitonicLoop(List<Point> points){
		if(points.size() < 2)
			return 0d;

		Map<Integer, Double> length = new HashMap<>(points.size()); // length of shortest open path containing points [0,K], but not including closing edge from K-1 to K
		// example: length.get(3) could contain the lengths 0->1, 1->2, and 0->3, which if we added the distance 2->3 would be a shortest loop for these four points in this case.
		Map<Integer, Integer> returnJourney = new HashMap<>(); // this hashMap contains the first point in the return journey, i.e. for a key N, the value will be the index of the point other than N-1 that has a direct connection to N

		// base cases
		length.put(1, points.get(0).distTo(points.get(1)));
		length.put(2, length.get(1) + points.get(0).distTo(points.get(2)));
		returnJourney.put(1, 0);
		returnJourney.put(2, 0);

		// expand the tables one by one
		for(int n = 3; n < points.size(); n++){
			double minLen = 0d;
			int minK = -1;

			double returnDist = 0d;
			for(int i = 1; i < n - 1; i++){
				returnDist += points.get(i).distTo(points.get(i + 1));
			}

			for(int k = 1; k <= n-1; k++){
				double currLen = length.get(k) + points.get(k - 1).distTo(points.get(n)) + returnDist;

				if(minK == -1 || currLen < minLen){
					minLen = currLen;
					minK = k;
				}

				returnDist -= points.get(k).distTo(points.get(k+1));
			}

			length.put(n, minLen);
			returnJourney.put(n, minK - 1);
		}

		// print path
		Set<Integer> returnPath = new HashSet<>();
		int idx = points.size() - 1; // take these indexes and use returnJourney table to jump back, keeping a record of where weve been
		while(idx != 0){ // by construction we will always hit 0 (see base cases for returnJourneys)
			System.out.println("<- " + points.get(idx));
			returnPath.add(idx);
			idx = returnJourney.get(idx);
		}

		for( ; idx < points.size(); idx++)
			if(!returnPath.contains(idx))
				System.out.println(points.get(idx) + " ->");

		return length.get(points.size() - 1) + points.get(points.size() - 1).distTo(points.get(points.size() - 2)); // close the gap I guess
	}

	static class Point{
		public int x;
		public int y;

		public Point(int x, int y){
			this.x = x;
			this.y = y;
		}

		public double distTo(Point pt){
			return Math.sqrt(sqDist(pt));
		}

		public long sqDist(Point pt){
			return (this.x - pt.x) * (this.x - pt.x) + (this.y - pt.y) * (this.y - pt.y);
		}

		@Override
		public String toString() {
			return "(" + x + "," + y + ")";
		}

		@Override
		public boolean equals(Object obj) {
			if(!(obj instanceof Point))
				return false;

			Point pt = (Point) obj;

			return this.x == pt.x && this.y == pt.y;
		}

		@Override
		public int hashCode() {
			return x * 31 + y;
		}
	}
}
