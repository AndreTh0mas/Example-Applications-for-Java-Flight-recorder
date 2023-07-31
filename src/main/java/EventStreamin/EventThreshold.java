package EventStreamin;

import java.util.Random;
import jdk.jfr.Event;
import jdk.jfr.Label;
import jdk.jfr.Name;
import jdk.jfr.Threshold;

/*
 In this example we have created a Custom event with an annotation of Threshold("50ms") which specifies what should the duration of the event for it
 to recorded, else it won't be recorded. This is important property as this allows us to record selective events for example if we want to record an event
 if the response time of the request is greater > 100ms.
*/
public class EventThreshold {
    @Name("com.spr.RandomSleep")
    @Label("Random Sleep")
    @Threshold("50 ms") // Won't record the event if the duration is less than threshold.
    static class RandomSleep extends Event {
        @Label("Event number")
        int eventNumber;
        @Label("Random Value")
        int randomValue;
    }
    public static void main(String... args) throws Exception {
        Random randNum = new Random();
        for (int i = 0; i < 10; i++) {
            RandomSleep event = new RandomSleep();
            event.begin();
            event.eventNumber = i;
            event.randomValue = Math.abs(randNum.nextInt() % 100);
            System.out.println("Event #" + i + ": " + event.randomValue);
            Thread.sleep(event.randomValue);
            event.commit(); // Automatically the duration of the event is looked for Threshold limit and then only it is recorded
        }
    }
}

