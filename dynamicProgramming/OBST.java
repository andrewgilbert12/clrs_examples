package dynamicProgramming;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import util.Node;

public class OBST<T> {
	private Map<Integer, Map<Integer, Integer>> costMemo; // costMemo.get(i).get(j) == cost of [i,j] OBST
	private Map<Integer, Map<Integer, Integer>> splitMemo; // costMemo.get(i).get(j) == cost of [i,j] OBST

	private Node<T> root;

	private int getCost(List<Node<T>> nodes, List<Integer> probs, int i, int j){
		if(j < i)
			return 0;
		if(i == j)
			return probs.get(i);
		if(costMemo.containsKey(i) && costMemo.get(i).containsKey(j))
			return costMemo.get(i).get(j);

		// calculate
		int minCost = Integer.MAX_VALUE;
		int totalProb = 0;
		int minSplit = 0;

		for(int split = i; split <= j; split++){
			totalProb += probs.get(split);
			int cost = getCost(nodes, probs, i, split - 1) + getCost(nodes, probs, split + 1, j) - probs.get(split);
			if(cost < minCost){
				minCost = cost;
				minSplit = split;
			}
		}
		minCost += totalProb; // we're pushing everything a layer down so it adds a singular cost here...except for the split which we subtracted while finding the min

		// add cost and split memo info
		if(!costMemo.containsKey(i))
			costMemo.put(i, new HashMap<>());
		costMemo.get(i).put(j, minCost);

		if(!splitMemo.containsKey(i))
			splitMemo.put(i, new HashMap<>());
		splitMemo.get(i).put(j, minSplit);

		return minCost;
	}

	private int getSplit(List<Node<T>> nodes, List<Integer> probs, int i, int j){
		if(j < i)
			throw new IllegalArgumentException("j must be >= i to have a split.");
		if(i == j)
			return i;

		getCost(nodes, probs, i, j);
		return splitMemo.get(i).get(j);
	}

	private Node<T> balance(List<Node<T>> nodes, List<Integer> probs, int i, int j) {
		if(j < i)
			return Node.emptyNode;
		if(i == j)
			return nodes.get(i);

		int split = getSplit(nodes, probs, i, j);
		Node<T> ret = nodes.get(split);
		ret.left = balance(nodes, probs, i, split - 1);
		ret.right = balance(nodes, probs, split + 1, j);

		return ret;
	}

	public OBST(List<Node<T>> nodes, List<Integer> probs){
		if(nodes.size() != probs.size())
			throw new IllegalArgumentException("Cost list and Node list must have same length");

		costMemo  = new HashMap<>();
		splitMemo = new HashMap<>();

		root = balance(nodes, probs, 0, nodes.size() - 1);
		// TODO: add to OBST in correct order
	}

	public int add(Node<T> node){
		return 0; // TODO add and return new cost
	}

	public int cost(){
		return 0; // TODO:
	}

	public Node<T> getRoot() {
		return root;
	}

	public static void main(String[] args){
		for(int N = 0; N <= 1000; N += 100){

			List<Node<Integer>> nodes = new ArrayList<>(N);
			for(int i = 1; i <= N; i++)
				nodes.add(new Node<>(i));

			List<Integer> probs = new ArrayList<>(N);
			Random r = new Random();
			for(int i = 1; i <= N; i++)
				probs.add(r.nextInt(N) + 1);

			long start = System.currentTimeMillis();

			OBST obst = new OBST<Integer>(nodes, probs);
//			obst.getRoot().printNode();
			System.out.println("did " + N + " in " + (System.currentTimeMillis() - start)/1000. + " sec.");
		}
	}
}
