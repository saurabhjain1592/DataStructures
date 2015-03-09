package union_find;

public class UnionFindLazyWithPathCompression {
	int a[];
	int s[];

	public UnionFindLazyWithPathCompression(int n) {
		a = new int[n];
		s = new int[n];
	}

	public boolean isConnected(int x, int y) {
		return find(x) == find(y);
	}

	public int find(int x) {
		if (x == a[x]) {
			return a[x];
		} else {
			a[x] = find(a[x]);
			return a[x];
		}
	}

	public void union(int x, int y) {
		int px = find(x);
		int py = find(y);
		if (s[px] > s[py]) {
			a[py] = a[px];
			s[px] += s[py];
		} else {
			a[px] = a[py];
			s[py] += s[px];
		}
	}
}
