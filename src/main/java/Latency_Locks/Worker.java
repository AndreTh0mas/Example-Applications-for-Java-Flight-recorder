package Latency_Locks;

/**
 * This is what will run in the worker threads. It will do some arbitrary work, and then log some
 * message about work being done.
 */
public class Worker implements Runnable {
	public final static Logger LOGGER = Logger.getLogger();
	private final int id;
	private final int loopCount;

	public Worker(int id, int loopCount) {
		this.id = id;
		this.loopCount = loopCount;
	}
	public void run() {
		while (true) {
			WorkEvent event = new WorkEvent();
			event.begin();
			int x = 1;
			int y = 1;
			for (int i = 1; i < loopCount; i++) {
				x += 1;
				y = y % (this.loopCount + 3);
				if (x % (this.loopCount + 4) == 0 || y == 0) {
					System.out.println("Should not happen");
				}
			}
			event.end();
			event.commit();
			LOGGER.log("Worker " + id + " reporting work done");
			Thread.yield();
		}
	}
}
