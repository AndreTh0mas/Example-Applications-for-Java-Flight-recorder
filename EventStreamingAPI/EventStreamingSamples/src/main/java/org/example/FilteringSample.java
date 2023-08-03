package org.example;

import jdk.jfr.*;

import java.io.IOException;
import java.util.Set;
import java.util.regex.Pattern;

/*
  This Example how we can use SettingDefinition annotation to commit those events which specifies some condition
  For example here we are only committing the FilteredSample event is the "message" field matches the regular expression "g.*"
*/

/*
  Running instruction.
  java -XX:StartFlightRecording:filename=filteringsample.jfr FilteringSample
  jfr print --events FilteredHello filteringsample.jfr
*/
public class FilteringSample {

    @Name("com.spr.FilteredHello")
    @Label("Hello With Message Filter")
    static class FilteredHello extends Event {
        @Label("Message")
        String message;
        @Label("Message Filter")
        @Description("Filters messages with regular expressions")
        @SettingDefinition
        protected boolean messageFilter(RegExpControl control) {
            return control.matches(message);
        }
    }

    public static void main(String[] args) throws IOException {
        try (Recording r = new Recording()) {
            r.enable(FilteredHello.class).with("messageFilter", "g.*");
            r.start();
            FilteredHello greenEvent = new FilteredHello();
            FilteredHello yellowEvent = new FilteredHello();
            FilteredHello redEvent = new FilteredHello();
            greenEvent.message = "green";
            yellowEvent.message = "yellow";
            redEvent.message = "red";
            greenEvent.commit();
            yellowEvent.commit();
            redEvent.commit();
        }
    }
}

class RegExpControl extends SettingControl {
    private Pattern pattern = Pattern.compile(".*");

    @Override
    public void setValue(String value) {
        this.pattern = Pattern.compile(value);
    }

    @Override
    public String combine(Set<String> values) {
        return String.join("|", values);
    }

    @Override
    public String getValue() {
        return pattern.toString();
    }

    public boolean matches(String s) {
        return pattern.matcher(s).find();
    }
}