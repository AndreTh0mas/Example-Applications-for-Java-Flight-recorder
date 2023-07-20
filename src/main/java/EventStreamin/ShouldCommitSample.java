package EventStreamin;

import java.util.Random;

import jdk.jfr.Event;
import jdk.jfr.Label;
import jdk.jfr.Name;
import jdk.jfr.Threshold;

public class ShouldCommitSample {

    @Name("com.oracle.RandomSleep")
    @Label("Random Sleep")
    @Threshold("20 ms")
    static class RandomSleep extends Event {
        @Label("ID")
        int id;
        @Label("Value Kind")
        String valueKind;
    }

    public static void main(String... args) throws Exception {
        Random randNum = new Random();
        for (int i = 0; i < 10; i++) {
            RandomSleep event = new RandomSleep();
            event.begin();
            event.id = i;
            int value = randNum.nextInt(40);
            System.out.println("ID " + i + ": " + value);
            Thread.sleep(value);
            event.end();
            if (event.shouldCommit()) { // checking for threshold condition and enabling of event/ event ran or not.
                // Format message outside timing of event
                if (value < 10) {
                    event.valueKind = "It was a low value of " + // These won't even be Printed ever as shouldCommit will be false for these
                            value + "!";
                } else if (value < 20) {
                    event.valueKind = "It was a normal value of " +
                            value + "!";
                } else {
                    event.valueKind = "It was a high value of " +
                            value + "!";
                }
                event.commit();
            }
        }
    }
}