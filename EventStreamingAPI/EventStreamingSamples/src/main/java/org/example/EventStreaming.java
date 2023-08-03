package org.example;

import jdk.jfr.Configuration;
import jdk.jfr.consumer.RecordingStream;

import java.nio.file.Path;
import java.time.Duration;


/*
This example showcases the functionality of EvenStreaming API. Here we are running a Recording Stream and printing the events fired = Custom events as
well as some internal jdk events such as jdk.CPULoad and jdk.ThreadSleep
*/
public class EventStreaming{
    public Path path;

    public static void main(String[] args) throws Exception{
        Configuration conf = Configuration.getConfiguration("default");
        try(RecordingStream stream = new RecordingStream(conf)){ // Starting a new Recording stream = Recording + Event Streaming on that recording
            stream.setMaxAge(Duration.ofSeconds(100)); // Duration of the recording
            stream.enable("jdk.CPULoad").withPeriod(Duration.ofSeconds(1)); // Enabling this event to be sampled every second.
            LoggerEvent event = new LoggerEvent();
            event.message = "This is Working correctly";
            stream.onEvent("jdk.ThreadSleep", System.out::println);
            stream.onEvent("LoggerEvent", System.out::println);
            stream.enable("LoggerEvent"); // enabling the Logger event
            System.out.println("Staring a recording stream");
            stream.startAsync(); // This sample calls startAsync(), which runs the stream in a background thread. While stream.start() is blocking.
            while(true){
                event.begin();
                System.out.println("Sleeping for 1s...");
                Thread.sleep(1000);
                event.commit();
            }
        }
    }
}
