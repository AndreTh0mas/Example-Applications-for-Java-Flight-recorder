package Latency_Locks;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Event;
import jdk.jfr.Label;

/**
 * Example event showing that not much is needed to time stuff using the JDK Flight Recorder.
 */
@Label("Work")
@Category("03_JFR_Latencies")
@Description("A piece of work executed")
public class WorkEvent extends Event {

}
