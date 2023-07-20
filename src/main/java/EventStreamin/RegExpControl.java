package EventStreamin;

import java.util.Set;
import java.util.regex.Pattern;

import jdk.jfr.SettingControl;

public class RegExpControl extends SettingControl {

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