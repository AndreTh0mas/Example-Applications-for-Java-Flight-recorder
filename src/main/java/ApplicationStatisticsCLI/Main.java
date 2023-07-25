package ApplicationStatisticsCLI;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

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

    public static void main(String[] args) {
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
                // Do the logic of Starting a New JFR Recording and printing report on that.
                System.out.println("Starting a new JFR recording");
                System.out.println("Specify the Duration of Recording: ,Press enter for default = 100s");

                System.out.println("Specify the Path of Recording: ,Press enter for default = This directory");

            } else {
                System.out.println("Java Process with PID: " + PID + " not found.");
                System.exit(0);
            }
        }
        else{
            // Looking for the main class.

        }
    }

}
