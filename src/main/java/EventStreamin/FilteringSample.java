package EventStreamin;

import java.io.IOException;

import jdk.jfr.Description;
import jdk.jfr.Event;
import jdk.jfr.Label;
import jdk.jfr.Name;
import jdk.jfr.Recording;
import jdk.jfr.SettingDefinition;

public class FilteringSample {

    @Name("com.oracle.FilteredHello")
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