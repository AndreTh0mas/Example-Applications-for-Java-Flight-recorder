package Latency_Locks;

/*
	Main Logger doing logging on operations
*/
public class Logger {
	private static Logger LoggerInstance = new Logger();
	public static Logger getLogger() {
		return LoggerInstance;
	}
	// Fix: remove the synchronized keyword for all threads to access log method concurrently.
	public synchronized void log(String text) {
		LoggingEvent event = new LoggingEvent(text);
		event.begin();
		// Do logging here
		// Write the text to a database or similar...
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// Do Nothing
		}
		event.end();
		event.commit();
	}
}
