package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

public class UndirectedWeightedGraphKruskalMST {

	private ArrayList<ArrayList<WeightedEdge>> adj;

	private int v;

	private int e;

	public UndirectedWeightedGraphKruskalMST(int v) {
		this.v = v;
		this.e = 0;
		this.adj = new ArrayList<ArrayList<WeightedEdge>>();
		for (int i = 0; i < v; i++) {
			this.adj.add(new ArrayList<WeightedEdge>());
		}
	}

	public void addEdge(int x, int y, double weight) {
		this.adj.get(x).add(new WeightedEdge(x, y));
		this.adj.get(y).add(new WeightedEdge(y, x));
		e++;
	}

	public HashSet<WeightedEdge> getMst() {
		HashSet<WeightedEdge> hs = new HashSet<WeightedEdge>();
		TreeSet<VertexEdgePair> ts = new TreeSet<VertexEdgePair>();
		HashMap<Integer, VertexEdgePair> hm = new HashMap<Integer, VertexEdgePair>();
		for (WeightedEdge we : this.adj.get(0)) {
			if (hm.get(we.endPoint2()) == null) {
				hm.put(we.endPoint2(), new VertexEdgePair(we.endPoint2(), we,
						0 + we.weight()));
				ts.add(new VertexEdgePair(we.endPoint2(), we, 0 + we.weight()));
			} else if (hm.get(we.endPoint2()).weight > 0 + we.weight()) {
				ts.remove(hm.get(we.endPoint2()));
				hm.put(we.endPoint2(), new VertexEdgePair(we.endPoint2(), we,
						0 + we.weight()));
				ts.add(new VertexEdgePair(we.endPoint2(), we, 0 + we.weight()));
			}
		}
		while (hs.size() != v - 1) {
			VertexEdgePair vep = ts.pollFirst();
			hs.add(vep.we);
			for (WeightedEdge we : this.adj.get(vep.v)) {
				if (hm.get(we.endPoint2()) == null) {
					hm.put(we.endPoint2(), new VertexEdgePair(we.endPoint2(),
							we, vep.weight + we.weight()));
					ts.add(new VertexEdgePair(we.endPoint2(), we, vep.weight
							+ we.weight()));
				} else if (hm.get(we.endPoint2()).weight > vep.weight
						+ we.weight()) {
					ts.remove(hm.get(we.endPoint2()));
					hm.put(we.endPoint2(), new VertexEdgePair(we.endPoint2(),
							we, vep.weight + we.weight()));
					ts.add(new VertexEdgePair(we.endPoint2(), we, vep.weight
							+ we.weight()));
				}
			}
		}
		return hs;
	}

	private class VertexEdgePair implements Comparable<VertexEdgePair> {

		private int v;

		private WeightedEdge we;

		private double weight;

		public VertexEdgePair(int v, WeightedEdge we, double weight) {
			this.v = v;
			this.we = we;
			this.weight = weight;
		}

		public int compareTo(VertexEdgePair vwp) {
			if (this.weight - vwp.weight != 0)
				return (int) (this.weight - vwp.weight);
			return this.v - vwp.v;
		}
	}
}

class WeightedEdge {

	private int x, y;

	private double weight;

	public WeightedEdge(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int endPoint1() {
		return x;
	}

	public int endPoint2() {
		return y;
	}

	public double weight() {
		return weight;
	}
}
