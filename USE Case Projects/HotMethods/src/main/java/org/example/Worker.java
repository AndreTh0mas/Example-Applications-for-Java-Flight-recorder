package org.example;

/*
   The code run in the worker threads. One JFR event will be generated per lap in the loop,
   recording the "result".
 */
public class Worker implements Runnable {
	public void run() {
		while (true) {
			WorkEvent event = new WorkEvent();
			event.begin();
			DataPoints i1 = new DataPoints();
			DataPoints i2 = new DataPoints();
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
