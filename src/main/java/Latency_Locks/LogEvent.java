package Latency_Locks;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Event;
import jdk.jfr.Label;

/**
 * This is the event emitted by our "Logger".
 */
@Label("Log Entry")
@Category("03_JFR_Latencies")
public class LogEvent extends Event {
	@Label("Message")
	@Description("The logged message")
	private String message;

	public LogEvent(String message) {
		this.message = message;
	} // Constructor

	public String getMessage() {
		return message;
	} // getter

	public void setMessage(String message) {
		this.message = message;
	} // setter
}
