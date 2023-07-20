package EventStreamin;

import jdk.jfr.consumer.EventStream;
import java.util.concurrent.atomic.AtomicInteger;

public class PassiveEventStream {
    static int NUMBER_CPULOAD_EVENTS = 3;
    // Like any other event stream, a passive event stream listens for events.
    // But what gets recorded is controlled by external means. for example, by the command-line option -XX:StartFlightRecording, the jcmd command JFR.start, or an API (for example, Recording::start())
    // it requires a recording to act on. Here it gets from command line option -XX:StartFlightRecording.
    public static void main(String[] args) throws Exception{
        AtomicInteger timer = new AtomicInteger();
        try (EventStream es = EventStream.openRepository()) {
            es.onEvent("jdk.CPULoad", event -> {
                System.out.println("CPU Load " + event.getEndTime());
                System.out.println(" Machine total: "
                        + 100 * event.getFloat("machineTotal") + "%");
                System.out.println(
                        " JVM User: " + 100 * event.getFloat("jvmUser") +
                                "%");
                System.out.println(
                        " JVM System: " + 100 * event.getFloat("jvmSystem") +
                                "%");
                System.out.println();
                if (timer.incrementAndGet() == NUMBER_CPULOAD_EVENTS) {
                    System.exit(0);
                }
            });
            es.start(); // Blocking call so try to use startAsync() or start in a new Thread.
        }
    }
}
