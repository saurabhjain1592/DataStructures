package hashing;

import java.util.Random;

public class BloomFilter<K, V> {
	private byte[] b;
	private int size;
	private final long randomFunction11;
	private final long randomFunction12;
	private final long randomFunction21;
	private final long randomFunction22;
	private final long randomFunction31;
	private final long randomFunction32;
	private final long randomFunction41;
	private final long randomFunction42;
	private final long randomFunction51;
	private final long randomFunction52;

	private final long hashMod = 2L << 61 - 1;;

	public BloomFilter(int size) {
		this.size = size;
		b = new byte[size];
		Random r = new Random();
		randomFunction11 = r.nextInt(Integer.MAX_VALUE);
		randomFunction12 = r.nextInt(Integer.MAX_VALUE);
		randomFunction21 = r.nextInt(Integer.MAX_VALUE);
		randomFunction22 = r.nextInt(Integer.MAX_VALUE);
		randomFunction31 = r.nextInt(Integer.MAX_VALUE);
		randomFunction32 = r.nextInt(Integer.MAX_VALUE);
		randomFunction41 = r.nextInt(Integer.MAX_VALUE);
		randomFunction42 = r.nextInt(Integer.MAX_VALUE);
		randomFunction51 = r.nextInt(Integer.MAX_VALUE);
		randomFunction52 = r.nextInt(Integer.MAX_VALUE);
	}

	public void put(K key, V value) {
		int h1 = hashFunction1(key);
		int i11 = h1 / 8; // h1 & 0x08
		int i12 = h1 % 8;
		b[i11] = (byte) (b[i11] | (1 << (8 - i12 - 1)));
		int h2 = hashFunction2(key);
		int i21 = h2 / 8;
		int i22 = h2 % 8;
		b[i21] = (byte) (b[i21] | (1 << (8 - i22 - 1)));
		int h3 = hashFunction3(key);
		int i31 = h3 / 8;
		int i32 = h3 % 8;
		b[i31] = (byte) (b[i31] | (1 << (8 - i32 - 1)));
		int h4 = hashFunction4(key);
		int i41 = h4 / 8;
		int i42 = h4 % 8;
		b[i41] = (byte) (b[i41] | (1 << (8 - i42 - 1)));
		int h5 = hashFunction5(key);
		int i51 = h5 / 8;
		int i52 = h5 % 8;
		b[i51] = (byte) (b[i51] | (1 << (8 - i52 - 1)));
	}

	private int hashFunction1(K key) {
		long result = ((randomFunction11 * key.hashCode()) % hashMod + randomFunction12)
				% hashMod;
		return (int) (result % (size * 8));
	}

	private int hashFunction2(K key) {
		long result = ((randomFunction21 * key.hashCode()) % hashMod + randomFunction22)
				% hashMod;
		return (int) (result % (size * 8));
	}

	private int hashFunction3(K key) {
		long result = ((randomFunction31 * key.hashCode()) % hashMod + randomFunction32)
				% hashMod;
		return (int) (result % (size * 8));
	}

	private int hashFunction4(K key) {
		long result = ((randomFunction41 * key.hashCode()) % hashMod + randomFunction42)
				% hashMod;
		return (int) (result % (size * 8));
	}

	private int hashFunction5(K key) {
		long result = ((randomFunction51 * key.hashCode()) % hashMod + randomFunction52)
				% hashMod;
		return (int) (result % (size * 8));
	}

	public boolean isThere(K key) {
		boolean flag = true;
		int h1 = hashFunction1(key);
		int i11 = h1 / 8;
		int i12 = h1 % 8;
		flag = flag && (b[i11] & 1 << (8 - i12 - 1)) != 0;
		int h2 = hashFunction2(key);
		int i21 = h2 / 8;
		int i22 = h2 % 8;
		flag = flag && (b[i21] & 1 << (8 - i22 - 1)) != 0;
		int h3 = hashFunction3(key);
		int i31 = h3 / 8;
		int i32 = h3 % 8;
		flag = flag && (b[i31] & 1 << (8 - i32 - 1)) != 0;
		int h4 = hashFunction4(key);
		int i41 = h4 / 8;
		int i42 = h4 % 8;
		flag = flag && (b[i41] & 1 << (8 - i42 - 1)) != 0;
		int h5 = hashFunction5(key);
		int i51 = h5 / 8;
		int i52 = h5 % 8;
		flag = flag && (b[i51] & 1 << (8 - i52 - 1)) != 0;
		return flag;
	}

	public static void main(String[] args) {
		BloomFilter<Integer, Integer> bf = new BloomFilter<>(541);
		for (int i = 0; i < 250; i++) {
			bf.put(i, i);
		}
		for (int i = 300; i < 400; i++) {
			bf.put(i, i);
		}
		for (int i = 0; i < 500; i++) {
			System.out.println(i + " " + bf.isThere(i));
		}
	}
}