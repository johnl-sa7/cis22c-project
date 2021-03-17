package com.company;

import java.util.ArrayList;

public class HashTable<T> {

	private int numElements;
	private ArrayList<List<T>> Table;

	/**
	 * Constructor for the hash table. Initializes the Table to be sized according
	 * to value passed in as a parameter Inserts size empty Lists into the table.
	 * Sets numElements to 0
	 * 
	 * @param size the table size
	 */
	public HashTable(int size) {
		this.Table = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			List<T> list = new List<>();
			Table.add(list);
		}
		this.numElements = 0;
	}

	/* Accessors */

	/**
	 * returns the hash value in the Table for a given Object
	 * 
	 * @param t the Object
	 * @return the index in the Table
	 */
	private int hash(T t) {
		int code = t.hashCode();
		return code % Table.size();
	}

	/**
	 * counts the number of keys at this index
	 * 
	 * @param index the index in the Table
	 * @precondition 0 <= index < Table.length
	 * @return the count of keys at this index
	 * @throws IndexOutOfBoundsException
	 */
	public int countBucket(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= Table.size()) {
			throw new IndexOutOfBoundsException("countBucket: Index is out of bounds");
		}

		List<T> list = Table.get(index);
		return list.getLength();
	}

	/**
	 * returns total number of keys in the Table
	 * 
	 * @return total number of keys
	 */
	public int getNumElements() {
		return numElements;
	}

	/**
	 * Accesses a specified key in the Table
	 * 
	 * @param t the key to search for
	 * @return the value to which the specified key is mapped, or null if this table
	 *         contains no mapping for the key.
	 * @precondition t != null
	 * @throws NullPointerException if the specified key is null
	 */
	public T get(T t) throws NullPointerException {
		if (t == null) {
			throw new NullPointerException("get() - t cannot be null!");
		}
		int bucket = hash(t);
		Table.get(bucket).placeIterator();
		while (!Table.get(bucket).offEnd()) {
			if (Table.get(bucket).getIterator().equals(t)) {
				return Table.get(bucket).getIterator();
			}
			Table.get(bucket).advanceIterator();
		}
		return null;
	}

	/**
	 * Determines whether a specified key is in the Table
	 * 
	 * @param t the key to search for
	 * @return whether the key is in the Table
	 * @precondition t != null
	 * @throws NullPointerException if the specified key is null
	 */
	public boolean contains(T t) throws NullPointerException {
		if (t == null) {
			throw new NullPointerException("contains() - t cannot be null!");
		}
		int bucket = hash(t);
		Table.get(bucket).placeIterator();
		while (!Table.get(bucket).offEnd()) {
			if (Table.get(bucket).getIterator().equals(t)) {
				return true;
			}
			Table.get(bucket).advanceIterator();
		}
		return false;
	}

	/* Mutators */

	/**
	 * Inserts a new element in the Table at the end of the chain in the bucket to
	 * which the key is mapped
	 * 
	 * @param t the key to insert
	 * @precondition t != null
	 * @throws NullPointerException for a null key
	 */
	public void put(T t) throws NullPointerException {
		if (t == null) {
			throw new NullPointerException("put() - t cannot be null!");
		}
		int bucket = hash(t);
		Table.get(bucket).addLast(t);
		numElements++;
	}

	/**
	 * removes the key t from the Table calls the hash method on the key to
	 * determine correct placement has no effect if t is not in the Table or for a
	 * null argument
	 * 
	 * @param t the key to remove
	 * @throws NullPointerException if the key is null
	 */
	public void remove(T t) throws NullPointerException {
		if (t == null) {
			throw new NullPointerException("remove() - t cannot be null!");
		}
		int bucket = hash(t);
		Table.get(bucket).placeIterator();
		while (!Table.get(bucket).offEnd()) {
			if (Table.get(bucket).getIterator().equals(t)) {
				Table.get(bucket).removeIterator();
				numElements--;
				return;
			}
			Table.get(bucket).advanceIterator();
		}
	}

	/**
	 * Clears this hash table so that it contains no keys.
	 */
	public void clear() {
		for (int i = 0; i < this.Table.size(); i++) {
			Table.set(i, new List<>());
		}
		this.numElements = 0;
	}

	/* Additional Methods */

	/**
	 * Prints all the keys at a specified bucket in the Table. Each key displayed on
	 * its own line, with a blank line separating each key Above the keys, prints
	 * the message "Printing bucket #<bucket>:" Note that there is no <> in the
	 * output
	 * 
	 * @param bucket the index in the Table
	 */
	public void printBucket(int bucket) {
		if (!Table.get(bucket).isEmpty()) {
			System.out.println("Printing bucket #" + bucket + ":");

			Table.get(bucket).placeIterator();
			for (int i = 0; i < Table.get(bucket).getLength(); i++) {
				System.out.print(Table.get(bucket).getIterator() + " ");
				Table.get(bucket).advanceIterator();
			}
			System.out.println();
		}
	}

	/**
	 * Prints the first key at each bucket along with a count of the total keys with
	 * the message "+ <count> -1 more at this bucket." Each bucket separated with
	 * two blank lines. When the bucket is empty, prints the message "This bucket is
	 * empty." followed by two blank lines
	 */
	public void printTable() {
		for (int bucket = 0; bucket < Table.size(); bucket++) {
			if (Table.get(bucket) != null && Table.get(bucket).getLength() > 0)
				System.out.print("Bucket " + bucket + ": " + Table.get(bucket).getFirst() + ","
						+ (Table.get(bucket).getLength() - 1) + " more at this bucket\n");
			else
				System.out.print("Bucket " + bucket + ": This bucket is empty\n");
		}
	}

	/**
	 * Starting at the first bucket, and continuing in order until the last bucket,
	 * concatenates all elements at all buckets into one String
	 */
	@Override
	public String toString() {
		StringBuilder r = new StringBuilder();
		for (List<T> tList : Table) {
			if (tList != null) {
				tList.placeIterator();
				for (int i = 0; i < tList.getLength(); i++) {
					r.append(tList.getIterator()).append("\n");
					tList.advanceIterator();
				}
			}
		}
		return r.toString();
	}

}

