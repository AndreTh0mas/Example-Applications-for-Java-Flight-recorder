
package HotMethods;

/**
 * The code run in the worker threads. One JFR event will be generated per lap in the loop,
 * recording the "result".
 */
public class Worker implements Runnable {
	public void run() {
		while (true) {
			WorkEvent event = new WorkEvent();
			event.begin();
			HolderOfUniqueValues i1 = new HolderOfUniqueValues();
			HolderOfUniqueValues i2 = new HolderOfUniqueValues();
			i1.initialize(3);
			i2.initialize(5);
			int intersectionSize = i1.countIntersection(i2);
			event.setIntersectionSize(intersectionSize);
			event.end();
			event.commit();
			Thread.yield();
		}
	}
}
