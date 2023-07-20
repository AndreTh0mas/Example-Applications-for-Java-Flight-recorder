package EventStreamin;

import jdk.jfr.consumer.RecordingStream;
import jdk.jfr.Configuration;
import java.nio.file.Path;
import java.time.Duration;

public class EventStreaming{
    public Path path;

    public static void main(String[] args) throws Exception{
        Configuration conf = Configuration.getConfiguration("default"); // might throw Exception
        try(RecordingStream stream = new RecordingStream(conf)){ // Starting a new recording stream.
            /*
                Question: What does it mean by starting a new recording stream?
                Answer:   Starting a new recording. and creates an event stream at the same time (A stream of events)
             */
            // More events we enable to read, more overhead we wll be having.
            // stream fields are defining as .set,.onEvent etc and hence we can run it as Asynchronous call with no worry.
            stream.setMaxAge(Duration.ofSeconds(100)); // Sample this event every one second
            stream.enable("jdk.CPULoad").withPeriod(Duration.ofSeconds(1)); // We don't have to do this when are using some configuration
            LoggerEvent event = new LoggerEvent();
            event.message = "This is Working correctly";
            stream.onEvent("jdk.ThreadSleep", System.out::println);
            stream.onEvent("LoggerEvent", System.out::println);
            stream.enable("LoggerEvent");
            System.out.println("Staring a recording stream");
            stream.startAsync(); //This sample calls startAsync(), which runs the stream in a background thread.
            // If you call the start() method, then the application will not proceed pass this method call until the stream is closed.
            while(true){
                event.begin();
                System.out.println("Sleeping for 1s...");
                Thread.sleep(1000);
                event.commit();
            }
        }
    }



}
