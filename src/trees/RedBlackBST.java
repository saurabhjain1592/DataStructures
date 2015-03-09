package trees;

public class RedBlackBST<T extends Comparable<T>> {
	RBNode root;

	public RedBlackBST() {
		root = null;
	}

	private enum Color {
		Red(0), Black(1);
		private final int value;

		Color(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

	}

	private class RBNode {
		RBNode left;
		RBNode right;
		RBNode parent;
		T data;
		Color color;

		public RBNode(T data) {
			this.left = null;
			this.parent = null;
			this.right = null;
			this.data = data;
			this.color = Color.Red;
		}
	}

	public void insert(T data) {
		root = insert(root, null, data);
		root.color = Color.Black;
	}

	private RBNode insert(RBNode r, RBNode parent, T data) {
		if (r == null) {
			RBNode newNode = new RBNode(data);
			newNode.parent = parent;
			return newNode;
		} else {
			if (data.compareTo(r.data) >= 0) {
				r.right = insert(r.right, r, data);
			} else {
				r.left = insert(r.left, r, data);
			}
			r = balanceTree(r);
			return r;
		}
	}

	private RBNode balanceTree(RBNode r) {
		if (r == null) {
			return r;
		}
		if (isRed(r.left) && isRed(r.right)) {
			flipColors(r);
		} else {
			if (r.left != null && isRed(r.left)) {
				if (isRed(r.left.left)) {
					r = rotateRight(r);
				}
				if (isRed(r.left.right)) {
					r.left = rotateLeft(r.left);
					r = rotateRight(r);
				}
			} else if (r.right != null && isRed(r.right)) {
				if (isRed(r.right.left)) {
					r.right = rotateRight(r.right);
					r = rotateLeft(r);
				}
				if (isRed(r.right.right)) {
					r = rotateLeft(r);
				}
			}
		}

		return r;
	}

	private boolean isRed(RBNode r) {
		if (r == null || r.color == Color.Black) {
			return false;
		} else {
			return true;
		}
	}

	private void flipColors(RBNode r) {
		if (r == null || r.left == null || r.right == null) {
			return;
		}
		r.color = Color.Red;
		r.left.color = Color.Black;
		r.right.color = Color.Black;
	}

	private RBNode rotateLeft(RBNode r) {
		if (r == null || r.right == null) {
			return r;
		}
		RBNode x = r.right;
		r.right.parent = r.parent;
		if (r.parent != null) {
			if (r.parent.left == r) {
				r.parent.left = r.right;
			} else {
				r.parent.right = r.right;
			}
		}
		r.parent = r.right;
		r.right = r.parent.left;
		if (r.parent.left != null)
			r.parent.left.parent = r;
		r.parent.left = r;

		r.parent.color = Color.Black;
		r.color = Color.Red;
		return x;
	}

	private RBNode rotateRight(RBNode r) {
		if (r == null || r.left == null) {
			return r;
		}
		RBNode x = r.left;
		r.left.parent = r.parent;
		if (r.parent != null) {
			if (r.parent.left == r) {
				r.parent.left = r.left;
			} else {
				r.parent.right = r.left;
			}
		}
		r.parent = r.left;
		r.left = r.parent.right;
		if (r.parent.right != null)
			r.parent.right.parent = r;
		r.parent.right = r;

		r.parent.color = Color.Black;
		r.color = Color.Red;
		return x;
	}

	public void inOrder() {
		inOrder(root);
	}

	private void inOrder(RBNode r) {
		if (r != null) {
			inOrder(r.left);
			System.out.println(r.data);
			inOrder(r.right);
		}
	}

	public static void main(String[] args) {
		RedBlackBST<Integer> rb = new RedBlackBST<Integer>();
		rb.insert(1);
		rb.insert(5);
		rb.insert(2);
		rb.insert(3);
		rb.insert(0);
		rb.insert(6);
		rb.inOrder();
	}
}