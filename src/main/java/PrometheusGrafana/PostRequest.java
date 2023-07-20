package PrometheusGrafana;

import java.util.Random;

public class PostRequest {
    public String getInput() {
        return Input;
    }
    public void setInput(String input) {
        Input = input;
    }
    String Input;
    public String Response () throws Exception{ // RequestBody can be anything such as ID etc.
        int RandomNumber = (int) (Math.random()*2000);
        Thread.sleep(RandomNumber); // Sleeping before giving out the response and value of sleep could go from
        //  0 to 2000 milliseconds
        return "Bearer"+Input; // Sending some random response;
    }

}
