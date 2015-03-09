package graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;

public class GraphAndDFS {
	private ArrayList<ArrayList<Integer>> graph;
	private int v;
	private int e;
	private boolean directed;

	public GraphAndDFS(int v) {
		this.v = v;
		this.e = 0;
		this.directed = false;
		graph = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < v; i++) {
			graph.add(new ArrayList<Integer>());
		}
	}

	public GraphAndDFS(GraphAndDFS g) {
		this.v = g.v;
		this.e = g.e;
		this.directed = g.directed;
		this.graph = new ArrayList<ArrayList<Integer>>();
		for (ArrayList<Integer> al : graph) {
			ArrayList<Integer> bl = new ArrayList<Integer>();
			bl.addAll(al);
			graph.add(bl);
		}
	}

	public void addEdge(int x, int y) {
		if (directed) {
			graph.get(x).add(y);
		} else {
			graph.get(x).add(y);
			graph.get(y).add(x);
		}
	}

	public boolean dfs(int x) {
		boolean b[] = new boolean[v];
		boolean flag = false;
		for (int i = 0; i < this.v && !flag; i++) {
			if (b[i] == true) {
				// do nothing
			} else {
				flag = dfsRec(i, x, b);
			}
		}
		return flag;
	}

	private boolean dfsRec(int s, int t, boolean b[]) {
		b[s] = true;
		if (s == t)
			return true;
		for (int c : graph.get(s)) {
			if (b[c] == true) {
				// do nothing
			} else {
				dfsRec(c, t, b);
			}
		}
		return false;
	}

	public int[] tropologicalSort() {
		MutableInteger num = new MutableInteger(this.v - 1);
		int t[] = new int[this.v];
		boolean b[] = new boolean[v];
		for (int i = 0; i < this.v; i++) {
			if (b[i]) {
				// do nothing
			} else {
				tropologicalSort(t, num, i, b);
			}
		}
		return t;
	}

	private void tropologicalSort(int t[], MutableInteger num, int i,
			boolean b[]) {
		b[i] = true;
		for (int c : this.graph.get(i)) {
			if (b[c]) {
				// do nothing
			} else {
				tropologicalSort(t, num, c, b);
			}
		}
		t[num.getValue()] = i;
		num.setValue(num.getValue() - 1);
	}

	public HashSet<HashSet<Integer>> connectedComponentsDirectedGraph() {
		if (directed) {
			GraphAndDFS reverse = this.reverseGraph();
			Stack<Integer> stack = new Stack<Integer>();
			// or int t[] = new int[this.v]; // and do tropological sort using
			// array
			boolean b[] = new boolean[this.v];
			for (int i = 0; i < reverse.v; i++) {
				if (b[i] == true) {
					// do nothing
				} else {
					reverse.tropologicalSort(stack, i, b);
				}
			}
			HashSet<HashSet<Integer>> bigSet = new HashSet<HashSet<Integer>>();
			b = new boolean[this.v];
			for (int top : stack) {
				if (b[top] == true) {
					// do nothing
				} else {
					HashSet<Integer> set = new HashSet<Integer>();
					dfsScc(set, top, b);
					bigSet.add(set);
				}
			}
			return bigSet;
		} else {
			return connectedComponents();
		}
	}

	public HashSet<HashSet<Integer>> connectedComponents() {
		HashSet<HashSet<Integer>> bigSet = new HashSet<HashSet<Integer>>();
		boolean b[] = new boolean[v];
		for (int i = 0; i < v; i++) {
			if (b[i] == true) {
				// do nothing
			} else {
				HashSet<Integer> set = new HashSet<Integer>();
				LinkedList<Integer> q = new LinkedList<Integer>();
				b[i] = true;
				q.addLast(i);
				set.add(i);
				while (!q.isEmpty()) {
					int c = q.removeFirst();
					for (int p : graph.get(c)) {
						if (b[p] == true) {
							// do nothing
						} else {
							b[p] = true;
							q.addLast(p);
							set.add(p);
						}
					}
				}
				bigSet.add(set);
			}
		}
		return bigSet;
	}

	private void dfsScc(HashSet<Integer> set, int i, boolean b[]) {
		set.add(i);
		b[i] = true;
		for (int c : this.graph.get(i)) {
			if (b[c] == true) {
				// do nothing
			} else {
				dfsScc(set, c, b);
			}
		}
	}

	private void tropologicalSort(Stack<Integer> stack, int i, boolean b[]) {
		b[i] = true;
		for (int c : this.graph.get(i)) {
			if (b[c] == true) {
				// do nothing
			} else {
				tropologicalSort(stack, c, b);
			}
		}
		stack.push(i);
	}

	private GraphAndDFS reverseGraph() {
		GraphAndDFS reverse;
		if (directed) {
			reverse = new GraphAndDFS(this.v);
			for (int i = 0; i < this.v; i++) {
				for (int x : this.graph.get(i)) {
					reverse.graph.get(x).add(i);
				}
			}
		} else {
			reverse = new GraphAndDFS(this);
		}
		return reverse;
	}

	private static class MutableInteger {
		int value;

		private MutableInteger(int value) {
			this.value = value;
		}

		private void setValue(int value) {
			this.value = value;
		}

		private int getValue() {
			return value;
		}
	}
}


