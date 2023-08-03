package Latency_Locks;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Event;
import jdk.jfr.Label;

/*
	Custom Logger Event to extract relevant info about the running application
*/
@Label("Log Entry")
@Category("JFR_Latencies")
public class LoggingEvent extends Event {
	@Label("Message")
	@Description("The logged message")
	private String message;
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	public LoggingEvent(String message) {
		this.message = message;
	}
}
