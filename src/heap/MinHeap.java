package heap;

public class MinHeap {
	int a[];
	int last;

	public MinHeap(int a[]) {
		this.a = new int[a.length + 1];
		for (int i = 0; i < a.length; i++) {
			this.a[i + 1] = a[i];
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
		while (i > 1 && a[i / 2] > a[i]) {
			swap(a, i / 2, i);
			i = i / 2;
		}
	}

	private void minHeapifyDown(int i) {
		while (2 * i <= last) {
			if (2 * i + 1 <= last) {
				int min = min(a[2 * i + 1], a[2 * i]);
				if (min < a[i]) {
					int tI = min == a[2 * i] ? 2 * i : 2 * i + 1;
					swap(a, tI, i);
					i = tI;
				} else {
					break;
				}
			} else {
				if (a[2 * i] < a[i]) {
					swap(a, 2 * i, i);
					i = 2 * i;
				} else {
					break;
				}
			}
		}
	}

	public boolean insert(int x) {
		if (last < a.length) {
			last++;
			a[last] = x;
			minHeapifyUp(last);
			return true;
		}
		return false;
	}

	public int extractMin() {
		if (last == 0) {
			return Integer.MIN_VALUE;
		} else {
			int x = a[1];
			swap(a, 1, last);
			last--;
			minHeapifyDown(1);
			return x;
		}
	}

	public int getMin() {
		return last > 0 ? a[1] : Integer.MIN_VALUE;
	}

	public boolean delete(int x) {
		int i = search(x, 1);
		if (i == -1) {
			return false;
		} else {
			swap(a, x, last);
			last--;
			minHeapifyDown(i);
			minHeapifyUp(i);
			return true;
		}
	}

	private int search(int x, int i) {
		if (i > last || a[i] > x) {
			return -1;
		}

		if (x == a[i]) {
			return i;
		} else {
			int p = search(x, 2 * i);
			int q = search(x, 2 * i + 1);
			return max(p, q);
		}
	}

	private int min(int a, int b) {
		return a < b ? a : b;
	}

	private int max(int a, int b) {
		return a > b ? a : b;
	}

	private void swap(int a[], int x, int y) {
		int temp = a[x];
		a[x] = a[y];
		a[y] = temp;
	}

	public static void main(String[] args) {

		int a1[] = { 5, 2, 1, 3, 6, 8, 9, 4, 7 };
		MinHeap h1 = new MinHeap(a1);
		System.out.println(h1.getMin());
		System.out.println(h1.extractMin());
		System.out.println(h1.insert(5));
		System.out.println(h1.getMin());
		System.out.println(h1.delete(9));
		System.out.println(h1.delete(12));
		System.out.println(h1.delete(h1.getMin()));
		System.out.println(h1.insert(0));
		int c1 = h1.extractMin();
		while (c1 != Integer.MIN_VALUE) {
			System.out.println(c1);
			c1 = h1.extractMin();
		}
	}
}
