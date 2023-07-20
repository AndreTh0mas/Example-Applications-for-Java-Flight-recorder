package EventStreamin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.Properties;

import com.sun.tools.attach.VirtualMachine;

import jdk.jfr.consumer.EventStream;
/*The sample StreamExternalEventsWithJcmdSample.java is similar to StreamExternalEventsWithAttachAPISample
 except it starts Flight Recorder for SleepOneSecondIntervals with the Attach API. With this API,
 the sample runs the command jcmd <PID> JFR.start with the PID of SleepOneSecondIntervals:*/

public class StreamExternalEventsWithJcmdSample {
    public static void main(String... args) throws Exception {
        if (args[0] == null) {
            System.err.println("Requires PID of process as argument");
            System.exit(1);
        }

        String pid = args[0];
        // Every Java application has a single instance of class Runtime that allows the application to interface
        // with the environment in which the application is running. The current runtime can be obtained from the getRuntime method.
        // An application cannot create its own instance of this class
        // Use in exec String[] not a single string as it is deprecated for plausible reasons.
        Process p = Runtime.getRuntime().exec( //Executes the specified string command in a separate process
                "jcmd " + pid + " JFR.start");
        // p -> Process object to manage the subprocess just created.
        printOutput(p); // To output the standard output and Error of the process here.
        // Wait for jcmd to start the recording
        Thread.sleep(1000);
        VirtualMachine vm = VirtualMachine.attach(pid);
        Properties props = vm.getSystemProperties();
        String repository = props.getProperty("jdk.jfr.repository");
        System.out.println("jdk.jfr.repository: " + repository);

        try (EventStream es = EventStream
                .openRepository(Paths.get(repository))) {
            System.out.println("Found repository ...");
            es.onEvent("jdk.ThreadSleep", System.out::println);
            es.start();
        }
    }

    private static void printOutput(Process proc) throws IOException {
        BufferedReader stdInput = new BufferedReader(
                new InputStreamReader(proc.getInputStream()));

        BufferedReader stdError = new BufferedReader(
                new InputStreamReader(proc.getErrorStream()));

        // Read the output from the command
        System.out.println(
                "Here is the standard output of the command:\n");
        String s = null;
        while ((s = stdInput.readLine()) != null) {
            System.out.println(s);
        }
        // Read any errors from the attempted command
        System.out.println(
                "Here is the standard error of the command (if any):\n");
        while ((s = stdError.readLine()) != null) {
            System.out.println(s);
        }
    }
}
