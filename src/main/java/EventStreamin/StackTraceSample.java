package EventStreamin;
import java.util.List;
import java.util.function.Consumer;
import jdk.jfr.Event;
import jdk.jfr.EventType;
import jdk.jfr.Label;
import jdk.jfr.Name;
import jdk.jfr.StackTrace;
import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordedFrame;
import jdk.jfr.consumer.RecordedStackTrace;
import jdk.jfr.consumer.RecordingStream;


/*
This example shows how we can access the stack trace of the event and print it.
Here we are only enabling the custom event com.Sprinklr.WithStackTrace and analyzing on that.
*/
public class StackTraceSample {
    @Name("com.spr.WithStackTrace")
    @Label("With Stack Trace")
    @StackTrace(true)
    static class WithStackTrace extends Event {
        String message;
    }

    static void firstFunc(int n) {
        if (n > 0) {
            secondFunc(n - 1);
        }
        WithStackTrace event = new WithStackTrace();
        event.message = "n = " + n;
        event.commit();

    }
    static void secondFunc(int n) {
        firstFunc(n);
    }
    public static void main(String... args) throws Exception {
        Consumer<RecordedEvent> myCon = x -> {
            EventType et = x.getEventType();
            System.out.println("Label: " + et.getLabel());
            System.out.println("Message: " + x.getValue("message"));
            RecordedStackTrace rst = x.getStackTrace();
            if (rst != null) {
                List<RecordedFrame> frames = rst.getFrames();
                System.out.println(
                        "Number of frames: " + frames.size());
                for (RecordedFrame rf : frames) {
                    System.out.println("Method, line number: "
                            + rf.getMethod().getName() + ", "
                            + rf.getLineNumber());
                }
            }
            System.out.print("\n");
        };
        try (RecordingStream rs = new RecordingStream()) {
            rs.onEvent("com.spr.WithStackTrace", myCon);
            rs.startAsync();
            firstFunc(5);
            rs.awaitTermination();// Blocks until all examples are completed or Interruption happens
        }
    }
}