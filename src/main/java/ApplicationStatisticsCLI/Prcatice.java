package ApplicationStatisticsCLI;
/*
Step-1: implement the priority queue and get unique threads with available stack traces which have the highest Thread CPU Load. upto 5 only.
*/

public class Prcatice {

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


    public static void main(String[] args) throws Exception{
        System.out.println(formatBytes(2.2820747294591194E8));
        System.out.println(formatPercentage(0.345));
        System.out.println(formatDuration(1.697604E7));
//        Path file = Path.of("/Users/harsh.kumar/Desktop/Directory1/health-report/src/file123.jfr");
//        try (var recordingFile = new RecordingFile(file)) { // Reads the recording from the file. From already recorded file
//            while (recordingFile.hasMoreEvents()) {
//                var e = recordingFile.readEvent(); // Reads the next event if exists
//                String eventName = e.getEventType().getName();
//                if(eventName.equals("jdk.ExecutionSample")){
////                    System.out.println(e);
//                    List<RecordedFrame> frames = e.getStackTrace().getFrames(); // getting all the frames from the Execution event.
//                    if (!frames.isEmpty()) {
//                        RecordedFrame topFrame = frames.get(0);
//                        if (topFrame.isJavaFrame()) {
//                            System.out.println(formatMethod(topFrame.getMethod()));
//                        }
//                    }
//
//                }
//
//            }
////            System.out.println("List of registered event types");
////                System.out.println("==============================");
////                for (EventType eventType : recordingFile.readEventTypes()) { // Returns the list of all event types.
////                    System.out.println(eventType.getName());
////                }
////
//        }
        Prcatice Harhs = new Prcatice();
//        Harhs.printReport();



//

    }
}

//                ArrayList<Pair<String,Float>> DataOnThreads = new ArrayList<>();
//                while(TopCPULoadUniqueThreads.size()>0){
//                    Pair<String,Float> Top = TopCPULoadUniqueThreads.poll();
//                    DataOnThreads.add(Top);
//                }
//                PriorityQueue<Pair<String, Float>> HotMethods5 = new PriorityQueue<>(customComparator);
//                for (Map.Entry<String, Float> entry : HotMethods.entrySet()) {
//                    float CountPercentage =(float) entry.getValue()/TotalCountForHotMethods;
//                    HotMethods.put(entry.getKey(),CountPercentage);
//                    HotMethods5.add(Pair.of(entry.getKey(), CountPercentage*100));
//                    if(HotMethods5.size()>5){
//                        HotMethods5.poll();
//                    }
//                }
//                while(HotMethods5.size()>0){
//                    var Temp = HotMethods5.poll();
//                    System.out.println(Temp.getLeft() + ":  "+ Temp.getRight()+"% ");
//                }