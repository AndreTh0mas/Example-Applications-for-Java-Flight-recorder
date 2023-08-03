package org.example;

import jdk.jfr.Event;
import jdk.jfr.Label;
import jdk.jfr.Name;
import jdk.jfr.Threshold;

import java.util.Random;

/*
This program shows ShouldCommit property of events: Which helps, as sometimes we only want to commit an event for some specified condition.
As committing an event always might be costly, and it would make our .jfr file very large.
*/
public class ShouldCommitSample {

    @Name("com.spr.RandomSleep")
    @Label("Random Sleep")
    @Threshold("20 ms")
    static class RandomSleepEvent extends Event {
        @Label("ID")
        int id;
        @Label("Value Kind")
        String valueKind;
    }
    public static void main(String... args) throws Exception {
        Random randNum = new Random();
        for (int i = 0; i < 10; i++) {
            RandomSleepEvent event = new RandomSleepEvent();
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