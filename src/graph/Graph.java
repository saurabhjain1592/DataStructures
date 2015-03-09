package graph;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;

public class Graph {

	private ArrayList<ArrayList<Integer>> graph;

	private int v;

	private int e;

	private boolean directed;

	private boolean weighted;

	private int w[][];

	public Graph(int v) {
		this.v = v;
		this.e = 0;
		this.directed = false;
		this.weighted = false;
		graph = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < v; i++) {
			graph.add(new ArrayList<Integer>());
		}
	}

	public Graph(int v, boolean directed) {
		this.v = v;
		this.e = 0;
		this.directed = directed;
		this.weighted = false;
		graph = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < v; i++) {
			graph.add(new ArrayList<Integer>());
		}
	}

	public Graph(int v, boolean directed, boolean weighted) {
		this.v = v;
		this.e = 0;
		this.directed = directed;
		this.weighted = weighted;
		if (this.weighted) {
			w = new int[v][v];
		}
		graph = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < v; i++) {
			graph.add(new ArrayList<Integer>());
		}
	}

	public Graph(Graph g) {
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

	public void addEdge(int x, int y, int w) {
		if (weighted) {
			if (directed) {
				graph.get(x).add(y);
				this.w[x][y] = w;
			} else {
				graph.get(x).add(y);
				this.w[x][y] = w;
				graph.get(y).add(x);
				this.w[y][x] = w;
			}
		} else {
			addEdge(x, y);
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
			Graph reverse = this.reverseGraph();
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

	private Graph reverseGraph() {
		Graph reverse;
		if (directed) {
			reverse = new Graph(this.v);
			for (int i = 0; i < this.v; i++) {
				for (int x : this.graph.get(i)) {
					reverse.graph.get(x).add(i);
				}
			}
		} else {
			reverse = new Graph(this);
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

	public int[] shortestPath(int i) throws Exception {
		if (i < 0 || i >= this.v) {
			throw new Exception();
		} else {
			int a[] = new int[this.v];
			VertexDistancePair vdp[] = new VertexDistancePair[this.v];
			for (int j = 0; j < a.length; j++) {
				if (i != j) {
					a[j] = Integer.MAX_VALUE;
					vdp[j] = new VertexDistancePair(j, a[j]);
				}
			}
			a[i] = 0;
			vdp[i] = new VertexDistancePair(i, 0);

			MinHeap<VertexDistancePair> minHeap = new MinHeap<VertexDistancePair>(
					vdp);
			HashSet<Integer> hs = new HashSet<Integer>();
			while (minHeap.getMin() != null) {
				VertexDistancePair temp = minHeap.extractMin();
				while (temp != null && hs.contains(temp.getV())) {
					temp = minHeap.extractMin();
				}
				if (temp == null)
					break;
				hs.add(temp.getV());
				if (temp.getD() == Integer.MAX_VALUE) {
					break;
				} else {
					for (int q : graph.get(temp.getV())) {
						if (!hs.contains(q)) {
							minHeap.deleteFastUsingVertex(q);
							a[q] = minInt(a[q], a[temp.getV()]
									+ w[temp.getV()][q]);
							minHeap.insert(new VertexDistancePair(q, a[q]));
						}
					}
				}
			}

			return a;
		}
	}

	private int minInt(int a, int b) {
		return a < b ? a : b;
	}

	private static class VertexDistancePair implements
			Comparable<VertexDistancePair> {

		private int v;

		private int d;

		public VertexDistancePair(int v, int d) {
			super();
			this.v = v;
			this.d = d;
		}

		public int getV() {
			return v;
		}

		public void setV(int v) {
			this.v = v;
		}

		public int getD() {
			return d;
		}

		public void setD(int d) {
			this.d = d;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + d;
			result = prime * result + v;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			VertexDistancePair other = (VertexDistancePair) obj;
			if (d != other.d)
				return false;
			if (v != other.v)
				return false;
			return true;
		}

		@Override
		public int compareTo(VertexDistancePair o) {
			int x = this.d - o.d;
			return x != 0 ? x : (this.v - o.v);
		}

	}

	private static class MinHeap<T extends VertexDistancePair> {

		T a[];

		int last;

		HashMap<Integer, Integer> posMp;

		public MinHeap(T a[]) {
			this.a = (T[]) Array.newInstance(a.getClass().getComponentType(),
					a.length + 1);
			posMp = new HashMap<Integer, Integer>();
			for (int i = 0; i < a.length; i++) {
				this.a[i + 1] = a[i];
				posMp.put(a[i].getV(), i + 1);
			}
			this.last = this.a.length - 1;
			for (int i = this.a.length / 2; i > last; i--) {
				minHeapify(i);
			}
		}

		private void minHeapify(int i) {
			minHeapifyUp(i);
			minHeapifyDown(i);
		}

		private void minHeapifyUp(int i) {
			while (i > 1 && a[i / 2].compareTo(a[i]) > 0) {
				swap(a, i / 2, i);
				i = i / 2;
			}
		}

		private void minHeapifyDown(int i) {
			while (2 * i <= last) {
				if (2 * i + 1 <= last) {
					T min = min(a[2 * i + 1], a[2 * i]);
					if (min.compareTo(a[i]) < 0) {
						int tI = min == a[2 * i] ? 2 * i : 2 * i + 1;
						swap(a, tI, i);
						i = tI;
					} else {
						break;
					}
				} else {
					if (a[2 * i].compareTo(a[i]) < 0) {
						swap(a, 2 * i, i);
						i = 2 * i;
					} else {
						break;
					}
				}
			}
		}

		public boolean insert(T x) {
			if (last < a.length) {
				last++;
				a[last] = x;
				posMp.put(x.getV(), last);
				minHeapifyUp(last);
				return true;
			}
			return false;
		}

		public T extractMin() {
			if (last == 0) {
				return null;
			} else {
				T x = a[1];
				swap(a, 1, last);
				last--;
				minHeapifyDown(1);
				return x;
			}
		}

		public T getMin() {
			return last > 0 ? a[1] : null;
		}

		public boolean deleteSimple(T x) {
			int i = searchSimple(x, 1);
			if (i == -1) {
				return false;
			} else {
				swap(a, i, last);
				last--;
				minHeapifyDown(i);
				minHeapifyUp(i);
				return true;
			}
		}

		private int searchSimple(T x, int i) {
			if (i > last || a[i].compareTo(x) > 0) {
				return -1;
			}

			if (x == a[i]) {
				return i;
			} else {
				int p = searchSimple(x, 2 * i);
				int q = searchSimple(x, 2 * i + 1);
				return max(p, q);
			}
		}

		public boolean deleteFast(T x) {
			int i = searchFast(x);
			if (i == -1) {
				return false;
			} else {
				swap(a, i, last);
				last--;
				minHeapifyDown(i);
				minHeapifyUp(i);
				return true;
			}
		}

		private int searchFast(T x) {
			if (posMp.get(x.getV()) > last) {
				return -1;
			} else {
				return posMp.get(x.getV());
			}
		}

		public boolean deleteFastUsingVertex(int v) {
			int i = searchFastUsingVertex(v);
			if (i == -1) {
				return false;
			} else {
				swap(a, i, last);
				last--;
				minHeapifyDown(i);
				minHeapifyUp(i);
				return true;
			}
		}

		private int searchFastUsingVertex(int v) {
			if (posMp.get(v) == null || posMp.get(v) > last) {
				return -1;
			} else {
				return posMp.get(v);
			}
		}

		private T min(T a, T b) {
			return a.compareTo(b) < 0 ? a : b;
		}

		private int max(int a, int b) {
			return a > b ? a : b;
		}

		private void swap(T a[], int x, int y) {
			posMp.put(a[x].getV(), y);
			posMp.put(a[y].getV(), x);
			T temp = a[x];
			a[x] = a[y];
			a[y] = temp;
		}
	}

	public static void main(String[] args) throws Exception {
		Graph g = new Graph(5, true, true);
		g.addEdge(0, 3, 4);
		g.addEdge(0, 1, 1);
		g.addEdge(1, 0, 3);
		g.addEdge(1, 2, 5);
		g.addEdge(1, 3, 2);
		g.addEdge(3, 2, 3);
		int a[] = g.shortestPath(0);
		for (int i = 0; i < a.length; i++) {
			System.out.println(a[i]);
		}
	}
}
