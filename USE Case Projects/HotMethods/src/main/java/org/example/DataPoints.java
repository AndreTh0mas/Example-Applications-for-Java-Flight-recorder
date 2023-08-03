package org.example;

import java.util.Collection;
import java.util.LinkedList;

public class DataPoints {
	private final Collection<Integer> collection;
	public DataPoints() {
		collection = new LinkedList<>();
	}

	// This creates a list of unique elements!
	public void initialize(int moduloDivisor) {
		collection.clear();
		for (int i = 1; i < 10000; i++) {
			if ((i % moduloDivisor) != 0)
				collection.add(i);
		}
	}

	protected Collection<Integer> getCollection() {
		return collection;
	}
	public int countIntersection(DataPoints other) {
		int count = 0;
		for (Integer i : collection) {
			if (other.getCollection().contains(i)) {
				count++;
			}
		}
		return count;
	}
}
