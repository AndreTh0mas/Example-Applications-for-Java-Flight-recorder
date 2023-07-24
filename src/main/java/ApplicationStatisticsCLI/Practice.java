package ApplicationStatisticsCLI;
/*
Step-1: implement the priority queue and get unique threads with available stack traces which have the highest Thread CPU Load. upto 5 only.
*/

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

public class Practice {

    String Wow;
    String harsh;
    String Kumar;
    String Mrinal;
    private static final String TEMPLATE =
            """
            ============================ APPLICATION STATS ===============================
            | GC: $GC_NAME            Phys. memory: $PHYSIC_MEM Alloc Rate: $ALLOC_RATE  |
            | OC Count    : $OC_COUNT Initial Heap: $INIT_HEAP  Total Alloc: $TOT_ALLOC  |
            | OC Pause Avg: $OC_AVG   Used Heap   : $USED_HEAP  Thread Count: $THREADS   |
            | OC Pause Max: $OC_MAX   Commit. Heap: $COM_HEAP   Class Count : $CLASSES   |
            | YC Count    : $YC_COUNT CPU Machine  : $MACH_CPU  Max Comp. Time: $MAX_COM |
            | YC Pause Max: $YC_MAX   CPU JVM USR+SYS: $SYS_CPU                           |
            |------------------------ Top Allocation Methods ----------------------------|
            | $ALLOCATION_TOP_FRAME                                            $AL_PE    |
            | $ALLOCATION_TOP_FRAME                                            $AL_PE    |
            | $ALLOCATION_TOP_FRAME                                            $AL_PE    |
            | $ALLOCATION_TOP_FRAME                                            $AL_PE    |
            | $ALLOCATION_TOP_FRAME                                            $AL_PE    |
            |----------------------------- Hot Methods ----------------------------------|
            | $EXECUTION_TOP_FRAME                                              $EX_PE   |
            | $EXECUTION_TOP_FRAME                                              $EX_PE   |
            | $EXECUTION_TOP_FRAME                                              $EX_PE   |
            | $EXECUTION_TOP_FRAME                                              $EX_PE   |
            | $EXECUTION_TOP_FRAME                                              $EX_PE   |
            ==============================================================================
            """;






    private static void writeParam(StringBuilder template, String variable, String value) { // Where the variable is $NAME
        int lastIndex = 0;
        while (true) {
            int index = template.indexOf(variable, lastIndex);
            if (index == -1) {
                return;
            }
            lastIndex = index + 1;
            if (value == null || value.length() == 0 ) {
                value = "N/A";
            }
            int length = Math.max(value.length(), variable.length());
            for (int i = 0; i < length; i++) {
                char c = i < value.length() ? value.charAt(i) : ' ';
                template.setCharAt(index + i, c);
            }
        }
    }
    private void printReport() {
        try {
            StringBuilder template = new StringBuilder(TEMPLATE);
//            for (var f : HealthReportPractice.class.getDeclaredFields()) { // Get all the fields of the class, this does not include Methods or any other thing
//                String variable = "$" + f.getName();
//                if (f.getType() == Field.class) { // Checking if the classes are the same
            //(Field) is the String variable to change in the template and f.get(this) is value to change it to.
//                    writeParam(template, variable, (Field) f.get(this)); // f.get(This) se we are getting the value of the Field
//                }
//            }
//            if (!scroll && printed) {
//                long lines = TEMPLATE.lines().count() + 2;
//                println("\u001b[" + lines + "A");
//            }
            System.out.println(template.toString());
        }
        catch(Exception ex){
            return;
        }
    }

    enum TimespanUnit {
        NANOSECONDS("ns", 1000), MICROSECONDS("us", 1000), MILLISECONDS("ms", 1000),
        SECONDS("s", 60), MINUTES("m", 60), HOURS("h", 24), DAYS("d", 7);

        final String text;
        final long amount;
        TimespanUnit(String unit, long amount) {
            this.text = unit;
            this.amount = amount;
        }
    }
    private static String formatDuration(Number value) {
        if (value == null || value.longValue()<=0) {
            return "N/A";
        }
        double t = value.doubleValue();
        TimespanUnit result = TimespanUnit.NANOSECONDS;
        for (TimespanUnit unit : TimespanUnit.values()) {
            result = unit;
            if (t < 1000) {
                break;
            }
            t = t / unit.amount;
        }
        return String.format("%.1f %s", t, result.text);
    }
    private static String formatPercentage(Number value) {
        if (value == null || value.longValue() == -1) {
            return "N/A";
        }
        return String.format("%6.2f %%", value.doubleValue() * 100);
    }

    private static String formatBytes(Number value) {
        if (value == null || value.longValue()<=0) {
            return "N/A";
        }
        long bytes = value.longValue();
        if (bytes >= 1024 * 1024) {
            return bytes / (1024 * 1024) + " MB";
        }
        if (bytes >= 1024) {
            return bytes / 1024 + " kB";
        }
        return bytes + " bytes";
    }

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
    public static void main(String[] args){
        if(args.length == 0 || args[0].equals("-help")){
            printHelp();
            System.exit(0);
        }
        // Checking if the argument is a file
        if(args[0].length()>4){
            String lastFourCharacters = args[0].substring(args[0].length() - 4);
            if(lastFourCharacters.equals(".jfr")){
                try{
                    Path file = Path.of(args[0]);
                    // Run the runner


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
