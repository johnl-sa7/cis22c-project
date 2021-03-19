package com.company;

import java.util.NoSuchElementException;

public class List<T> {
	private class Node {
		private final T data;
		private Node next;
		private Node prev;

		public Node(T data) {
			this.data = data;
			this.next = null;
			this.prev = null;
		}
	}

	private int length;
	private Node first;
	private Node last;
	private Node iterator; // add

	/* CONSTRUCTOR */

	/**
	 * Instantiates a new List with default values
	 *
	 * @postcondition Creating object variables
	 */
	public List() {
		length = 0;
		first = last = null;
		iterator = null; // add
	}

	/**
	 * Instantiates a new List by copying another List
	 *
	 * @param original the List to make a copy of
	 * @postcondition a new List object, which is an identical but separate copy of
	 *                the List original
	 */
	public List(List<T> original) {
		if (original == null) {
			return;
		}
		if (original.length == 0) {
			this.length = 0;
			this.first = null;
			this.last = null;
			this.iterator = null;
		} else {
			Node temp = original.first;
			while (temp != null) {
				addLast(temp.data);
				temp = temp.next;
			}
			iterator = null;
		}
	}

	/* ACCESSORS */

	/**
	 * Returns the value stored in the first node
	 *
	 * @precondition check that first node exists
	 * @return the value stored at node first
	 * @throws NoSuchElementException when precondition is violated
	 */
	public T getFirst() throws NoSuchElementException {
		if (length == 0) {
			throw new NoSuchElementException("getFirst(): list is empty!");
		}
		return first.data;
	}

	/**
	 * Returns the value stored in the last node
	 *
	 * @precondition check that last node exists
	 * @return the value stored in the node last
	 * @throws NoSuchElementException when precondition is violated
	 */
	public T getLast() throws NoSuchElementException {
		if (length == 0) {
			throw new NoSuchElementException("getLast(): list is empty!");
		}
		return last.data;
	}

	/**
	 * Returns the current length of the list
	 *
	 * @return the length of the list from 0 to n
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Returns whether the list is currently empty
	 *
	 * @return whether the list is empty
	 */
	public boolean isEmpty() {
		return length == 0;
	}

	/**
	 * Returns the element currently pointed at by the iterator
	 *
	 * @precondition check that the iterator exists
	 * @return the element pointed at by the iterator
	 * @throws NoSuchElementException when precondition is violated
	 */
	public T getIterator() throws NullPointerException {
		if (iterator == null) {
			throw new NullPointerException("getIterator(): iterator is off end.");
		}
		return iterator.data;
	}

	/**
	 * Returns whether the list is offEnd
	 *
	 * @return whether the list is offEnd
	 */
	public boolean offEnd() {
		return iterator == null;
	}

	/**
	 * Determines whether two Lists have the same data in the same order
	 *
	 * @param L the List to compare to this List
	 * @return whether the two Lists are equal
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof List)) {
			return false;
		} else {
			List<T> L = (List<T>) o;
			if (this.length != L.length) {
				return false;
			} else {
				Node temp1 = this.first;
				Node temp2 = L.first;
				while (temp1 != null) { // Lists are same length
					if (!(temp1.data.equals(temp2.data))) {
						return false;
					}
					temp1 = temp1.next;
					temp2 = temp2.next;
				}
				return true;
			}
		}
	}

	/* MUTATORS */

	/**
	 * Creates a new first element
	 *
	 * @param data the data to insert at the front of the list
	 * @postcondition A new node exists at the front of the list
	 */
	public void addFirst(T data) {
		if (first == null) {
			first = last = new Node(data);
		} else {
			Node N = new Node(data);
			N.next = first;
			first.prev = N;
			first = N;
		}
		length++;
	}

	/**
	 * Creates a new last element
	 *
	 * @param data the data to insert at the end of the list
	 * @postcondition A new node exists at the end of the list
	 */
	public void addLast(T data) {
		if (first == null) {
			first = last = new Node(data);
		} else {
			Node N = new Node(data);
			last.next = N;
			N.prev = last;
			last = N;
		}
		length++;
	}

	/**
	 * removes the element at the front of the list
	 *
	 * @precondition at least one node exists in the list
	 * @postcondition the first node from the list has been removed
	 * @throws NoSuchElementException when precondition is violated
	 */
	public void removeFirst() throws NoSuchElementException {
		if(iterator == first) iterator = null;

		if (length == 0) {
			throw new NoSuchElementException("removeFirst(): Cannot remove from an empty List!");
		} else if (length == 1) {
			first = last = null;
		} else {
			first = first.next;
			first.prev = null;
		}
		length--;
	}

	/**
	 * removes the element at the end of the list
	 *
	 * @precondition at least one node exists in the list
	 * @postcondition the last node from the list has been removed
	 * @throws NoSuchElementException when precondition is violated
	 */
	public void removeLast() throws NoSuchElementException {
		if(iterator == first || iterator == last) iterator = null;

		if (length == 0) {
			throw new NoSuchElementException("removeLast(): Cannot remove from an empty List!");
		} else if (length == 1) {
			first = last = null;
		} else {
			last = last.prev;
			last.next = null;
		}
		length--;
	}

	/**
	 * moves iterator to the start of the list
	 *
	 * @postcondition iterator will be at the first node
	 */
	public void placeIterator() {
		iterator = first;
	}

	/**
	 * removes the element currently referenced by the iterator
	 *
	 * @precondition iterator != null
	 * @throws NullPointerException when iterator is off end
	 * @postcondition iterator will be null
	 */
	public void removeIterator() throws NullPointerException {
		if (iterator == null) { // precondition
			throw new NullPointerException("removeIterator: iterator is off end.");
		} else if (iterator == first) { // edge case
			removeFirst(); // should set iterator to null in this case
		} else if (iterator == last) { // edge case
			removeLast(); // should set iterator to null in this case
		} else { // general case
			iterator.next.prev = iterator.prev;
			iterator.prev.next = iterator.next;
			length--;
		}
		iterator = null;

	}

	/**
	 * inserts an element after the node currently pointed to by the iterator
	 *
	 * @precondition iterator != null
	 * @throws NullPointerException when iterator is off end
	 * @postcondition element will be added to the list
	 */
	public void addIterator(T data) throws NullPointerException {
		if (iterator == null) { // precondition
			throw new NullPointerException("addIterator: iterator is off end.");
		} else if (iterator == last) { // edge case
			addLast(data);
		} else { // general case
			Node N = new Node(data);
			N.next = iterator.next;
			N.prev = iterator;
			iterator.next.prev = N;
			iterator.next = N;
			length++;
		}
	}

	/**
	 * moves the iterator up by one node
	 *
	 * @precondition check that iterator exists
	 * @postcondition iterator is moved up by one node
	 * @throws NullPointerException when precondition is violated
	 */
	public void advanceIterator() throws NullPointerException {
		if (iterator == null) {
			throw new NullPointerException("advanceIterator(): iterator is off end.");
		}
		iterator = iterator.next;
	}

	/**
	 * moves the iterator down by one node
	 *
	 * @precondition check that last node exists
	 * @postcondition iterator is moved down by one node
	 * @throws NullPointerException when precondition is violated
	 */
	public void reverseIterator() throws NullPointerException {
		if (iterator == null) {
			throw new NullPointerException("reverseIterator(): iterator is off end.");
		}
		iterator = iterator.prev;
	}

	/**** ADDITIONAL OPERATIONS ****/

	/**
	 * List with each value on its own line At the end of the List a new line
	 *
	 * @return the List as a String for display
	 */
	@Override
	public String toString() {
		String result = "";
		Node temp = first;
		while (temp != null) {
			result += temp.data + " ";
			temp = temp.next;
		}
		return result + "\n";
	}

	/**
	 * prints contents of the linked list to the screen in the format #. <element>
	 * followed by a newline
	 *
	 * @return print a numbered list
	 */
	public void printNumberedList() {
		Node val = first;
		System.out.println();
		int count = 1;
		while (val != null) {
			System.out.println((count++) + ". " + val.data);
			val = val.next;
		}
	}

	/**
	 * Points the iterator at first
	 * and then advances it to the
	 * specified index
	 * @param index the index where
	 * the iterator should be placed
	 * @precondition 0 < index <= length
	 * @throws IndexOutOfBoundsException
	 * when precondition is violated
	 */
	public void iteratorToIndex(int index) throws IndexOutOfBoundsException{
		if(index > length) {
			throw new IndexOutOfBoundsException("iteratorToIndex(): index exceeds total length of list");
		}
		else {
			iterator = first;
			for(int i = 1; i != index; i++) {
				iterator = iterator.next;
			}
		}
	}

	/**
	 * Searches the List for the specified
	 * value using the linear search algorithm
	 * @param value the value to search for
	 * @return the location of value in the
	 * List or -1 to indicate not found
	 * Note that if the List is empty we will
	 * consider the element to be not found
	 * @post: position of the iterator remains
	 * unchanged
	 */
	public int linearSearch(T value) {
		//return -1 if length == 0?
		Node temp = first;
		int i = 1;
		while(temp != null && !temp.data.equals(value)) {
			temp = temp.next;
			i++;
		}
		return temp == null ? -1 : i;
	}
}