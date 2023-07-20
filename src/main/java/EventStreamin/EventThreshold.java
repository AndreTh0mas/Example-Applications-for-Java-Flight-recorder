package EventStreamin;

import java.util.Random;
import jdk.jfr.Event;
import jdk.jfr.Label;
import jdk.jfr.Name;
import jdk.jfr.Threshold;

public class EventThreshold {

    @Name("com.oracle.RandomSleep")
    @Label("Random Sleep")
    @Threshold("50 ms") // Won't record the event if the duration is less than threshold.
    static class RandomSleep extends Event {
        @Label("Event number")
        int eventNumber;
        @Label("Random Value")
        int randomValue;
    }
    // Created a custom event and we are only Committing the event with Duration greater than the threshold.
    public static void main(String... args) throws Exception {
        Random randNum = new Random();
        for (int i = 0; i < 10; i++) {
            RandomSleep event = new RandomSleep();
            event.begin();
            event.eventNumber = i;
            event.randomValue = Math.abs(randNum.nextInt() % 100);
            System.out.println("Event #" + i + ": " + event.randomValue);
            Thread.sleep(event.randomValue);
            event.commit(); // Automatically the duration of the event is looked for Threshold limit and only then recorded.
        }
    }
}

// Use the command on the recorded file jfr print --events <Event to print (RandomSleep)> <filename.jfr>
