package org.example;

import jdk.jfr.Category;
import jdk.jfr.Event;
import jdk.jfr.Label;


@Label("Logger_Event")
@Category("JFR_Recording")
public class LoggerEvent extends Event {
    @Label("Type of Operation")
    public String WhichOperation;
    @Label("Thread Number")
    public String ThreadNumber;
    @Label("Regex matches")
    public int MatchCount;
}
