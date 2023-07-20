package EventStreamin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

import jdk.jfr.Event;
import jdk.jfr.EventType;
import jdk.jfr.Label;
import jdk.jfr.Name;
import jdk.jfr.Recording;
import jdk.jfr.consumer.EventStream;
import jdk.jfr.consumer.RecordingFile;

// NO USE JUST USE JAVA MISSION CONTROL TO VISUALIZE THE EVENTS:

public class ParsingRecordingFileSample {
    @Name("com.sprinklr.Hello") // Name of the event (Important)
    @Label("Hello World!")
    static class Hello extends Event { // Custom Events
        @Label("Greeting")
        String greeting;
    }
    @Name("com.sprinklr.Message")
    @Label("Message")
    static class Message extends Event {
        @Label("Text")
        String text;
    }
    public static void main(String... args) throws Exception{

        try (Recording r = new Recording()) { // Creates a recording without any settings
            r.start(); // Starting the recording
            r.enable("jdk.CPULoad").withPeriod(Duration.ofSeconds(1));
            for (int i = 0; i < 3; i++) {
                Message messageEvent = new Message();
                messageEvent.begin();
                messageEvent.text = "Message " + i;
                messageEvent.commit();

                Hello helloEvent = new Hello();
                helloEvent.begin();
                helloEvent.greeting = "Hello " + i;
                helloEvent.commit();
            }
            // Only these 3 events will be recorded.
            Thread.sleep(2000);
            r.stop(); // Stopping the recording
            Path file = Files.createTempFile("recording", ".jfr");
            // Creates an empty file in default temporary file directory and returns the PATH to that file
            r.dump(file); // Dumping the recording to destination

            try (var recordingFile = new RecordingFile(file)) { // Reads the recording from the file. From already recorded file
                System.out.println("Reading events one by one");
                System.out.println("=========================");
                while (recordingFile.hasMoreEvents()) {
                    var e = recordingFile.readEvent(); // Reads the next event if exists
                    String eventName = e.getEventType().getName();
                    System.out.println("Name: " + eventName);
                }
                System.out.println();
                System.out.println("List of registered event types");
                System.out.println("==============================");
                for (EventType eventType : recordingFile.readEventTypes()) { // Returns the list of all event types.
                    System.out.println(eventType.getName());
                }
            }
            System.out.println();

            System.out.println("Reading all events at once");
            System.out.println("==========================");

            for (var e : RecordingFile.readAllEvents(file)) {// Returns a list of al events that occurred in the recording
                // Not good use for the cases of Large files, only useful for simple cases.
                String eventName = e.getEventType().getName();
                System.out.println("Name: " + eventName);
            }
            System.out.println();

            System.out.println("Reading events one by one, printing only com.sprinklr.Message events");
            System.out.println("========================================="
                    + "=========================");
            /*
             To process only specific events, we could read events one by one with RecordingFile.readEvent(),
             as above, then check the event's name. However, if we use the event streaming API,
             then event objects of the same type are reused to reduced allocation pressure.
             */
            try (EventStream eventStream = EventStream.openFile(file)) {
                // Creates an event stream from a file.
                // By default, the stream starts with the first event in the file.
                eventStream.onEvent("com.sprinklr.Message", e -> {
                    System.out.println(
                            "Name: " + e.getEventType().getName());
                });
                eventStream.start();
            }
        }
    }
}
