
package AutoBoxingGC;
import java.io.IOException;

public class Allocations {
	private static final int NUMBER_OF_THREADS = 5;
	public static void main(String[] args) throws IOException {
		ThreadGroup threadGroup = new ThreadGroup("Workers");
		Thread[] threads = new Thread[NUMBER_OF_THREADS];
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(threadGroup, new Allocator(), "Allocator Thread " + i);
			threads[i].setDaemon(true);
			threads[i].start();
		}
		System.out.print("Press <enter> to quit!");
		System.out.flush();
		System.in.read();
	}
}
