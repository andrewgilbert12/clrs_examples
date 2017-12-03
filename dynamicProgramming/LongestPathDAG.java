package dynamicProgramming;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.management.RuntimeErrorException;

import com.google.common.graph.ImmutableValueGraph;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraph;
import com.google.common.graph.ValueGraphBuilder;

public class LongestPathDAG {

	public static void main(String[] args){
		MutableValueGraph<String, Double> mDag = ValueGraphBuilder.directed().build();
		mDag.addNode("A");
		mDag.addNode("B");
		mDag.addNode("Z");
		mDag.putEdgeValue("A", "B", 10.0);
		mDag.putEdgeValue("A", "B", 100.0);
		mDag.putEdgeValue("B", "C", 30.0);
		mDag.putEdgeValue("C", "Z", 5.0);
		mDag.putEdgeValue("A", "Z", 20.0);
		mDag.putEdgeValue("B", "Z", -10.0);
		ImmutableValueGraph<String, Double> dag = ImmutableValueGraph.copyOf(mDag);

		System.out.println(longestPath(dag, "A", "Z"));

	}

	// not dynamic...
	// dynamic solution:
	// base case: max length of A -> A = 0.0 because acyclic
	// for A -> Z
	// consider all paths A -> X -> Z where X is a node between A and Z
	// (use successors of A, etc. to be easier? rather than all nodes in grpah)
	// return maximum
	public static <N> double longestPath(ValueGraph<N, ? extends Double> dag, N start, N end){
		if(!dag.isDirected())
			throw new IllegalArgumentException("Only works with directed graphs");
		if(!dag.nodes().contains(start))
			throw new IllegalArgumentException("Start node " + start + " is not an element of the graph");
		if(!dag.nodes().contains(end))
			throw new IllegalArgumentException("End node " + end + " is not an element of the graph");

		Set<N> nodes = dag.nodes();
		Map<N, Double> maxTo = new HashMap<>(nodes.size());
		Deque<N> nodeStack = new ArrayDeque<>();
		Deque<Double> distStack = new ArrayDeque<>();

		for(N firstNode : dag.successors(start)){
			nodeStack.push(firstNode);
			Double firstDist = dag.edgeValue(start, firstNode);
			distStack.push(firstDist);
			if(!maxTo.containsKey(firstNode))
				maxTo.put(firstNode, firstDist);
			else
				maxTo.put(firstNode, Math.max(firstDist, maxTo.get(firstNode)));
		}

		while(!nodeStack.isEmpty()){
			N currNode = nodeStack.pop();
			Double currDist = distStack.pop();

			if(currNode.equals(end))
				continue; // don't go beyond the end since it's acyclic

			if(maxTo.get(currNode) > currDist)
				continue; // if we've already been to this node and had a longer trip ignore it

			for(N nextNode : dag.successors(currNode)){
				// TODO: we could also check here and not add to the stack if a longer path already exists?
				nodeStack.push(nextNode);
				Double distToNext = currDist + dag.edgeValue(currNode, nextNode);
				distStack.push(distToNext);
				if(!maxTo.containsKey(nextNode))
					maxTo.put(nextNode, distToNext);
				else
					maxTo.put(nextNode, Math.max(distToNext,maxTo.get(nextNode)));
			}
		}

		if(maxTo.containsKey(end))
			return maxTo.get(end);

		throw new IllegalArgumentException("A path from " + start + " to " + end + " doesn't exist");
	}
}
