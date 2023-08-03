
package org.example;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/*
	In this we are checking for values of the Map if they are present or not. It is like a unit test.
*/
public final class Allocator implements Runnable {
	private final static int SET_SIZE = 10_000;
	private final Map<Integer, MapObject> map;

	public Allocator() {
		map = createMap(SET_SIZE);
	}

	@Override
	public void run() {
		long yieldCounter = 0;
		while (true) {
			Collection<MapObject> myAllocSet = map.values();
			for (MapObject c : myAllocSet) {
				if (!map.containsKey(c.getId()))
					System.out.println("Should not happen!");
				if (++yieldCounter % 1000 == 0)
					Thread.yield();
			}
		}
	}
	private static Map<Integer, MapObject> createMap(int count) {
		Map<Integer, MapObject> map = new HashMap<>();
		// Here we are adding a primitive value where Reference object is expected.
		// To fix this do Integer.value(i) where Reference object is expected such as while putting in a HashMap<Integer,>
		for (int i = 0; i < count; i++) {
			map.put(i, new MapObject(i));
//			map.put(Integer.valueOf(i),new MapObject(Integer.valueOf(i)); // To Fix
		}
		return map;
	}
}
