package Latency_Locks;


public class Logger {
	private static Logger myInstance = new Logger();

	public static Logger getLogger() {
		return myInstance;
	}

	public synchronized void log(String text) {
		LogEvent event = new LogEvent(text);
		event.begin();
		// Do logging here
		// Write the text to a database or similar...
		try {

			Thread.sleep(200);
		} catch (InterruptedException e) {

		}
		event.end();
		event.commit();
	}
}
