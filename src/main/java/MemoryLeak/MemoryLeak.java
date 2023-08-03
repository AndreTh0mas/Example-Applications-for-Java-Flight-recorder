package MemoryLeak;

import java.util.HashMap;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

public class MemoryLeak {
	private static class DemoObject {
		private long position;
		@SuppressWarnings("unused")
		long myField1 = 1;
		@SuppressWarnings("unused")
		long myField2 = 2;
		@SuppressWarnings("unused")
		char[] chunk = new char[255];

		public DemoObject(int pos) {
			position = pos;
		}

		// Overriding the native methods
		public int hashCode() {
			return (int) position;
		}

		public boolean equals(Object o) {
			return (o instanceof DemoObject) && (o.hashCode() == position);
		}
	}

	private static class TransientAllocator implements Runnable {
		public void run() {
			while (true) {
				// Alloc transients
				List<Object> junkList = new ArrayList<Object>();
				for (int i = 0; i < 1000; i++) {
					junkList.add(new Object());
					for (int j = 0; j < 10; j++)
						// Keep busy yielding for a little while...
						Thread.yield();
				}
			}
		}
	}
	private static void startAllocThread() {
		Thread thread = new Thread(new TransientAllocator(), "Transient Allocator");
		thread.setDaemon(true);
		thread.start();
	}

	private static class DemoThread implements Runnable {
		private HashMap<DemoObject, String> Map;
		private int leakspeed;
		DemoThread(HashMap<DemoObject, String> Map, int leakspeed) {
			this.Map = Map;
			this.leakspeed = leakspeed;
		}
		public void run() {
			int total = 0;
			while (true) {
				for (int i = 0; i <= 100; i++)
					put(total + i);

				for (int i = 0; i <= 100 - leakspeed; i++) // leaving some of the objects as it is, in the HashMap.(MemoryLeak)
					remove(total + i);

				for (int i = 0; i < 10; i++) {
					// Keep busy yielding for a little while...
					Thread.yield();
				}
				try {
					Thread.sleep(70);
				} catch (InterruptedException e) {
				}
			}
		}
		private void put(int n) {
			Map.put(new DemoObject(n), "foo");
		}
		private String remove(int n) {
			return Map.remove(new DemoObject(n));
		}
	}
	private static final int NUMBER_OF_THREADS = 4;
	public static void main(String[] args) throws IOException {
		HashMap<DemoObject, String> LeakStore = new HashMap<>();
		Thread[] threads;
		int leakspeed = 2; // Represents number of objects we are leaving in the HashMap
		threads = new Thread[NUMBER_OF_THREADS];
		System.out.println(String.format("Starting leak with %d threads and a leak speed of %d", NUMBER_OF_THREADS, leakspeed));
		System.out.print("Starting threads... ");
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(new DemoThread(LeakStore, leakspeed));
			threads[i].setDaemon(true);
			threads[i].start();
		}
		System.out.println("done!");
		startAllocThread();
		System.out.println("Press <enter> to quit!");
		System.in.read();
	}
}