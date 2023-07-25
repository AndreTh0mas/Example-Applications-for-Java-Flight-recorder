package ApplicationStatisticsCLI;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static void printHelp(){
        System.out.println("---------------------------USAGE--------------------------------");
        System.out.println("java ApplicationStatistics.java [options] <main-class|pid|file>\n");
        System.out.println("--------------------------EXAMPLES------------------------------");
        System.out.println("java HealthReport.java MyApplication");
        System.out.println("java HealthReport.java 4711");
        System.out.println("java HealthReport.java PATH/recording.jfr\n");
        List<VirtualMachineDescriptor> vmDescriptors = VirtualMachine.list();
        System.out.println("--------------------Running Java Processes-----------------------");
        System.out.println("PID\t    DisplayName");
        System.out.println("---     -----------");
        boolean Count = false;
        for (VirtualMachineDescriptor vmDescriptor : vmDescriptors) {
            String pid = vmDescriptor.id();
            String displayName = vmDescriptor.displayName();
            System.out.println(pid + "\t" + displayName);
            Count = true;
        }
        if(!Count){
            System.out.println("Found no running Java processes");
        }
    }
    private static int parseInteger(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException nfe) {
            System.out.println("Not a valid PID value: " + value);
            System.exit(0);
        }
        return 0;
    }
    private static void printStdOutput(Process proc) throws IOException {
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
    public static Path RecordingStartUsingPID(long PID) throws Exception{
        Scanner scanner = new Scanner(System.in);
        System.out.println("Starting a new JFR recording");
        System.out.print("Specify the Duration of Recording in seconds (Press enter for default = 100s): ");
        String InputDuration = scanner.nextLine();
        int duration = 100;
        if (!InputDuration.trim().isEmpty()) {
            // Parse the user input to an integer if it's not empty
            try {
                duration = Integer.parseInt(InputDuration);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Using default duration of " + duration + " seconds.");
            }
        }
        String DurationInJFR = duration+"s";
        System.out.println("Specify the Path of Recording:Example: /Users/harsh.kumar/Desktop ,Press enter for default = This directory");
        String InputPath = scanner.nextLine();
        String currentPath = Paths.get("").toAbsolutePath() + "FlightRecorder.jfr";
        if(!InputPath.trim().isEmpty()){
            try{
                Path path = Paths.get(InputPath);
                System.out.println("The path is valid: "+path);
                currentPath = InputPath+"FlightRecorder.jfr";
            }
            catch(Exception ex){
                System.out.println("Invalid Path given: Using default path-> "+currentPath);
            }
        }
        // Starting the recording.
        Process p = Runtime.getRuntime().exec( //Executes the specified string command in a separate process
                "jcmd " + PID + " JFR.start "+ "duration="+DurationInJFR+" filename="+currentPath+" name=myrecording settings=profile stackdepth=64");
        // p -> Process object to manage the subprocess just created.
        printStdOutput(p); // To output the standard output and Error of the process here.
        // Wait for jcmd to start the recording
        System.out.println("If you see JFR recording did not start: close the process and try again");
        Thread.sleep(1000);
        // Waiting for the recording to complete
        Thread.sleep((long)duration*1000 + 10000); // Waiting for 10 extra seconds than duration for everything to complete.
        return Paths.get(currentPath);
    }

    public static void main(String[] args) throws Exception{
        if(args.length == 0 || args[0].equals("-help")){
            printHelp();
            System.exit(0);
        }
        ApplicationStatistics ApplicationReport= new ApplicationStatistics();
        // Checking if the argument is a file
        if(args[0].length()>4){
            if(args[0].endsWith(".jfr")){
                try{
                    Path file = Path.of(args[0]);
                    // Run the runner
                    ApplicationReport.Runner(file);
                    System.exit(0);
                } catch (Exception ex){
                    System.out.println("Invalid path of the jfr file or the file does not exists");
                    System.exit(0);
                }
            }
        }

        // Not a JFR file
        // Have to check for Main class or The pid of the process and then going start a new JFR recording.
        if(Character.isDigit(args[0].charAt(0))){
            // Must be the PID of a Java process.
            long PID = parseInteger(args[0]);
            Optional<ProcessHandle> processHandle = ProcessHandle.of(PID);
            // Check if the process is present (running)
            if (processHandle.isPresent()) {
                System.out.println("Java Process with PID: " + PID + " found.");
                try{
                    Path JfrFilepath = RecordingStartUsingPID(PID);
                    if(Files.exists(JfrFilepath)){
                        ApplicationReport.Runner(JfrFilepath);
                    }
                    else{
                        System.out.println("Jfr recording was unsuccessful please try again with proper arguments");
                    }
                }
                catch (Exception ex){
                    System.out.println("Something went wrong, Please try again");
                    ex.printStackTrace();
                    System.exit(0);
                }
            } else {
                System.out.println("Java Process with PID: " + PID + " not found.");
            }
        }
        else{
            // Looking for the Main class.
            RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
            // Get the process ID of the current Java process
            long currentPid = ProcessHandle.current().pid();
            // Iterating through the list of running Java processes
            for (String processInfo : runtimeMxBean.getInputArguments()) {
                if (processInfo.contains(args[0])) {
                    // Extract the PID from the processInfo (it is usually the first part before the '@' symbol)
                    int atIndex = processInfo.indexOf('@');
                    if (atIndex > 0) {
                        long pid = Long.parseLong(processInfo.substring(0, atIndex));
                        // Check if the found PID is different from the current PID to avoid self-matching
                        if (pid != currentPid) {
                            System.out.println("Found Java process with Main class '" + args[0] + "'. PID: " + pid);
                            try{
                                Path JfrFilepath = RecordingStartUsingPID(pid);
                                if(Files.exists(JfrFilepath)){
                                    ApplicationReport.Runner(JfrFilepath);
                                }
                                else{
                                    System.out.println("Jfr recording was unsuccessful please try again with proper arguments");
                                }
                                return;
                            }
                            catch (Exception ex){
                                System.out.println("Something went wrong, Please try again");
                                ex.printStackTrace();
                            }
                            break;
                        }
                    }
                }
            }
            System.out.println("Main Class: "+args[0]+" Not Found");
        }
    }

}
