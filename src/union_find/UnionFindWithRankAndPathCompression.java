package union_find;

public class UnionFindWithRankAndPathCompression {
	int a[];
	int r[];

	public UnionFindWithRankAndPathCompression(int n) {
		a = new int[n];
		r = new int[n];
	}

	public boolean isConnected(int x, int y) {
		return find(x) == find(y);
	}

	public void union(int x, int y) {
		int px = find(x);
		int py = find(y);
		if (r[px] > r[py]) {
			a[py] = a[px];
		} else if (r[px] == r[py]) {
			a[py] = a[px];
			r[px] += 1;
		} else {
			a[px] = a[py];
		}
	}

	public int find(int x) {
		if (x == a[x]) {
			return a[x];
		} else {
			a[x] = find(a[x]);
			return a[x];
		}
	}
}
