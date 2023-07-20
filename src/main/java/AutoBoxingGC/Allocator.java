
package AutoBoxingGC;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Will allocate a map with ExampleMapContent, and then proceeds to check for each ExampleMapContent
 * in the map.
 */
public final class Allocator implements Runnable {
	private final static int SET_SIZE = 10_000;
	private final Map<Integer, ExampleMapContent> map;

	public Allocator() {
		map = createMap(SET_SIZE);
	}

	@Override
	public void run() {
		long yieldCounter = 0;
		while (true) {
			Collection<ExampleMapContent> myAllocSet = map.values();
			for (ExampleMapContent c : myAllocSet) {
				if (!map.containsKey(c.getId()))
					System.out.println("Now this is strange!");
				if (++yieldCounter % 1000 == 0)
					Thread.yield(); //  relenqueshing the resource of the processor but can be rescheduled.
			}
		}
	}
	private static Map<Integer, ExampleMapContent> createMap(int count) {
		Map<Integer, ExampleMapContent> map = new HashMap<>();
		for (int i = 0; i < count; i++) {
			map.put(i, new ExampleMapContent(Integer.valueOf(i)));
		}
		return map;
	}
}
