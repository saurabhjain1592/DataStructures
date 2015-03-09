package graph;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.NoSuchElementException;

public class WeightedGraphPrimsMST {

	private ArrayList<ArrayList<WeightedEdge>> adj;

	private int v;

	private int e;

	public WeightedGraphPrimsMST(int v) {
		this.v = v;
		adj = new ArrayList<ArrayList<WeightedEdge>>();
		for (int i = 0; i < v; i++) {
			adj.add(new ArrayList<WeightedEdge>());
		}
	}

	private class WeightedEdge implements Comparable<WeightedEdge> {

		private int v;

		private int w;

		private int weight;

		public WeightedEdge(int v, int w, int weight) {
			this.v = v;
			this.w = w;
			this.weight = weight;
		}

		public int from() {
			return v;
		}

		public int to() {
			return w;
		}

		public double weight() {
			return weight;
		}

		@Override
		public int hashCode() {
			return this.v;
		}

		@Override
		public int compareTo(WeightedEdge vwp) {
			if (this.v == vwp.v)
				return 0;
			return this.weight - vwp.weight != 0 ? (this.weight - vwp.weight)
					: (this.v - vwp.v);
		}
	}

	public void addEdge(int v, int w, int weight) {
		adj.get(v).add(new WeightedEdge(v, w, weight));
	}

	public HashSet<WeightedEdge> primsMst() {
		HashSet<WeightedEdge> edgeSet = new HashSet<WeightedEdge>();
		WeightedEdge vwp[] = (WeightedEdge[]) Array.newInstance(
				WeightedEdge.class, v);
		for (int i = 0; i < v; i++) {
			vwp[i] = new WeightedEdge(i, i, Integer.MAX_VALUE);
		}
		for (WeightedEdge we : adj.get(0)) {
			vwp[we.w] = we;
		}
		HashSet<Integer> vertexSet = new HashSet<Integer>();
		vertexSet.add(0);
		MinHeap<WeightedEdge> mh = new MinHeap<WeightedEdge>(vwp);
		while (vertexSet.size() != v) {
			WeightedEdge temp = mh.extractMin();
			for (WeightedEdge twe : adj.get(temp.v)) {
				if (mh.get(twe).weight > twe.weight) {
					mh.delete(twe);
					mh.insert(twe);
				}
			}
			vertexSet.add(temp.v);
			edgeSet.add(temp);
		}
		return edgeSet;
	}

	private class MinHeap<T extends Comparable<T>> {

		private T a[];

		private int size;

		private int last = 0;

		private HashMap<T, Integer> hm = new HashMap<T, Integer>();

		public MinHeap(T a[]) {
			this.a = (T[]) Array.newInstance(a.getClass().getComponentType(),
					a.length + 1);
			for (int i = 1; i < this.a.length; i++) {
				this.a[i] = a[i - 1];
				hm.put(this.a[i], i);
			}
			for (int i = a.length / 2; i >= 1; i++) {
				minHeapify(i);
			}
		}

		private void minHeapify(int i) {
			minHeapifyUp(i);
			minHeapifyDown(i);
		}

		private void minHeapifyUp(int i) {
			while (i > 1) {
				if (a[i].compareTo(a[i / 2]) < 0) {
					specialSwap(a, i, i / 2);
					i = i / 2;
				} else {
					break;
				}
			}
		}

		private void minHeapifyDown(int i) {
			while (2 * i <= last) {
				if (2 * i + 1 <= last) {
					T min = min(a[2 * i], a[2 * i + 1]);
					int minI;
					if (min == a[2 * i]) {
						minI = 2 * i;
					} else {
						minI = 2 * i + 1;
					}
					if (a[i].compareTo(a[minI]) > 0) {
						specialSwap(a, minI, i);
					} else {
						break;
					}
				} else {
					if (a[i].compareTo(a[2 * i]) > 0) {
						specialSwap(a, 2 * i, i);
					} else {
						break;
					}
				}
			}
		}

		public void insert(T ele) throws ArrayIndexOutOfBoundsException {
			if (last >= a.length - 1) {
				throw new ArrayIndexOutOfBoundsException();
			}
			last++;
			a[last] = ele;
			hm.put(ele, last);
			minHeapifyUp(last);
		}

		public T extractMin() throws NoSuchElementException {
			if (last == 0) {
				throw new NoSuchElementException();
			}
			T retValue = a[0];
			specialSwap(a, 0, last);
			hm.remove(retValue);
			last--;
			minHeapifyDown(0);
			return retValue;
		}

		public T delete(T ele) throws NoSuchElementException {
			Integer x = hm.get(ele);
			if (x == null) {
				throw new NoSuchElementException();
			}
			T retValue = a[x];
			hm.remove(retValue);
			specialSwap(a, x, last);
			last--;
			minHeapifyUp(x);
			minHeapifyDown(x);
			return retValue;
		}

		public T get(T ele) throws NoSuchElementException {
			Integer x = hm.get(ele);
			if (x == null) {
				throw new NoSuchElementException();
			}
			T retValue = a[x];
			return retValue;
		}

		private void specialSwap(T a[], int i, int j) {
			hm.put(a[i], j);
			hm.put(a[j], i);
			T temp = a[i];
			a[i] = a[j];
			a[j] = temp;
		}

		private T min(T a, T b) {
			return a.compareTo(b) < 0 ? a : b;
		}
	}
}
