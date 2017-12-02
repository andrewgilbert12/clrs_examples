package util;

import java.util.ArrayDeque;
import java.util.Deque;

public class Node<T> {
	public static final Node emptyNode = new Node(null);

	public Node<T> left;
	public Node<T> right;
	private T val;

	public Node(T val){
		this.val   = val;
		this.left  = emptyNode;
		this.right = emptyNode;
	}

	public boolean isEmpty(){
		return this == emptyNode;
	}

	public T getVal() {
		return val;
	}

	public void printNode(){

		if(this.isEmpty())
			System.out.println("is empty node");
		else {
			Deque<Node<T>> stack     = new ArrayDeque<>();
			Deque<T> parentSt  = new ArrayDeque<>();
			Deque<Boolean> leftSt    = new ArrayDeque<>();

			System.out.println("Root node has value " + val);

			stack.push(right);
			leftSt.push(false);
			parentSt.push(val);

			stack.push(left);
			leftSt.push(true);
			parentSt.push(val);

			while(!stack.isEmpty()){

				Node<T> curr = stack.pop();
				T parentVal = parentSt.pop();
				boolean left = leftSt.pop();

				if(!curr.isEmpty()){
					System.out.print(left ? "Left" : "Right");
					System.out.println(" of " + parentVal + " is " + curr.val);

					if(!curr.right.isEmpty()){
						stack.push(curr.right);
						leftSt.push(false);
						parentSt.push(curr.val);
					}

					if(!curr.left.isEmpty()){
						stack.push(curr.left);
						leftSt.push(true);
						parentSt.push(curr.val);
					}
				}
			}
		}
	}
}
