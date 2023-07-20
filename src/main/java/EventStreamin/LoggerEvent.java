package EventStreamin;

import jdk.jfr.Category;
import jdk.jfr.Event;
import jdk.jfr.Label;

@Category("Eventstreaming on custom events")
@Label("LoggerEvent")
public class LoggerEvent extends Event {
    @Label("Message")
    String message;
}
