package ApplicationStatisticsCLI;
/*
Step-1: implement the priority queue and get unique threads with available stack traces which have the highest Thread CPU Load. upto 5 only.
*/

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordingFile;

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
    public static void main(String[] args){
//
            Path file = Path.of("/Users/harsh.kumar/Downloads/flight_recording-2.jfr");
        try (RecordingFile recordingFile = new RecordingFile(file)) { // Reads the events from the file. From already recorded file
            int count = 0;

            System.out.println(count);
        }
        catch (Exception ex){

        }

    }
}
