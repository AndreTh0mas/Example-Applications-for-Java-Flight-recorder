package org.example;

import jdk.jfr.*;
import jdk.jfr.consumer.EventStream;
import jdk.jfr.consumer.RecordingFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;


/*
In this example we have created 2 custom events and we are going to make a recording of the program then analyze it by parsing the recording file
This is useful if tools such as JDK mission control is not available or if one need to get some specific out of event data.
*/
public class ParsingRecordingFileSample {
    @Name("com.spr.Hello")
    @Label("Hello World!")
    static class Hello extends Event {
        @Label("Greeting")
        String greeting;
    }
    @Name("com.spr.Message")
    @Label("Message")
    static class Message extends Event {
        @Label("Text")
        String text;
    }
    public static void main(String... args) throws Exception{

        try (Recording r = new Recording()) {
            r.start();
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
            Thread.sleep(2000);
            r.stop();
            Path file = Files.createTempFile("recording", ".jfr");
            // Creates an empty file in default temporary file directory and returns the PATH to that file
            r.dump(file); // Dumping the recording to destination

            try (var recordingFile = new RecordingFile(file)) { // Reads the recording from the file. From already recorded file
                System.out.println("Reading events one by one");
                System.out.println("=========================");
                while (recordingFile.hasMoreEvents()) {
                    var e = recordingFile.readEvent();
                    String eventName = e.getEventType().getName();
                    System.out.println("Name: " + eventName);
                }
                System.out.println();
                System.out.println("List of registered event types");
                System.out.println("==============================");
                for (EventType eventType : recordingFile.readEventTypes()) {
                    System.out.println(eventType.getName());
                }
            }
            System.out.println();
            System.out.println("Reading all events at once");
            System.out.println("==========================");

            for (var e : RecordingFile.readAllEvents(file)) {
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
             then event objects of the same type are reused to reduce allocation pressure.
             */
            try (EventStream eventStream = EventStream.openFile(file)) {
                // Creates an event stream from the file.
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
