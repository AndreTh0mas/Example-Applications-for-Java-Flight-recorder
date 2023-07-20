package org.example;

import jdk.jfr.Category;
import jdk.jfr.Event;
import jdk.jfr.Label;

@Category( "FileReadEvent")
@Label("File read indication")

public class FileReadEvent extends Event {
    @Label("Indicator")
    boolean Indicator;
}
