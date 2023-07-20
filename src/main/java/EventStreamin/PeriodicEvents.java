package EventStreamin;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

import jdk.jfr.Event;
import jdk.jfr.FlightRecorder;
import jdk.jfr.Label;
import jdk.jfr.Name;
import jdk.jfr.Period;

// Calculates number of threads created and started every second
public class PeriodicEvents {
    private static ThreadMXBean tBean =
            ManagementFactory.getThreadMXBean();

    @Name("com.oracle.StartedThreadCount")
    @Label("Total number of started threads")
    @Period("1 s") // How often the event should be emitted (Like the CPU load event)
    static class StartedThreadCount extends Event {
        long totalStartedThreadCount;
    }

    public static void main(String[] args) throws InterruptedException {

        Runnable hook = () -> { // Lambda expression (call back method) that creates and commits events.
            StartedThreadCount event = new StartedThreadCount();
            event.totalStartedThreadCount =
                    tBean.getTotalStartedThreadCount();
            event.commit();
        };

        FlightRecorder.addPeriodicEvent(StartedThreadCount.class, hook);// To add the event to the Recording
        // The second argument is a callback method that's represented by a lambda expression
        // that creates and commits the event: This type similar to events such as CPULoad

        for (int i = 0; i < 4; i++) {
            Thread.sleep(1500);
            Thread t = new Thread();
            t.start();
        }
        FlightRecorder.removePeriodicEvent(hook);
        // In this we can do Recording.disable(Class name) of the event as well but this is better to avoid Memory leaks
    }
}