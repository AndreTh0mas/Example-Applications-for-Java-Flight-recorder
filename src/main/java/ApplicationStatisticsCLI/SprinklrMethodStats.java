package ApplicationStatisticsCLI;

import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordedFrame;
import jdk.jfr.consumer.RecordingFile;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ApplicationStatisticsCLI.Formatters.*;
public class SprinklrMethodStats {
    private final HashMap<String,HashMap<String,Float>> MethodWiseSprinklrCOM = new HashMap<>();
    private final HashMap<String,Float> TotalSprinklrCOM= new HashMap<>();
    private static long TotalExecutionEvent = 0;
    HashMap<String,Float> MethodSet;
    public SprinklrMethodStats( HashMap<String,Float> MethodSet){
        this.MethodSet = MethodSet;
    }
    private static final String SprinklrMethodTemplate = """
            | $METHOD_NAME                                                      $FX_PE   |
            |        --------------------------------------------------------            |
            | $HOT_METHOD_COM_SPRINKLR                                          $JX_PE   |
            | $HOT_METHOD_COM_SPRINKLR                                          $JX_PE   |
            | $HOT_METHOD_COM_SPRINKLR                                          $JX_PE   |
            | $HOT_METHOD_COM_SPRINKLR                                          $JX_PE   |
            | $HOT_METHOD_COM_SPRINKLR                                          $JX_PE   |
            ==============================================================================
            """;
    private static final String SprinklrTopMethodTemplate = """
            |--------------------------Top Sprinklr Methods------------------------------|
            | $HOT_TOP_COM_SPRINKLR                                             $KX_PE   |
            | $HOT_TOP_COM_SPRINKLR                                             $KX_PE   |
            | $HOT_TOP_COM_SPRINKLR                                             $KX_PE   |
            | $HOT_TOP_COM_SPRINKLR                                             $KX_PE   |
            | $HOT_TOP_COM_SPRINKLR                                             $KX_PE   |
            ==============================================================================
            """;
    public void printSprinklrStats(){
        List<Map.Entry<String, Float>> sortedEntriesHotMethods = MethodSet.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Float>comparingByValue().reversed())
                .toList();
        String variable = "N/A";
        String value = "N/A";
        for(Map.Entry<String,Float> entry: sortedEntriesHotMethods){
            StringBuilder MethodTemplate = new StringBuilder(SprinklrMethodTemplate);
            List<Map.Entry<String, Float>> sortedOnSprinklrMethod = MethodWiseSprinklrCOM.get(entry.getKey()).entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, Float>comparingByValue().reversed())
                    .toList();
            variable = "$METHOD_NAME";
            value = entry.getKey();
            writeParam(MethodTemplate,variable,value);
            variable = "$FX_PE";
            value = formatPercentage(entry.getValue()/TotalExecutionEvent);
            writeParam(MethodTemplate,variable,value);
            for(int i = 0;i<5;i++){
                variable = "$HOT_METHOD_COM_SPRINKLR";
                value = "N/A";
                if(i<sortedOnSprinklrMethod.size()) {
                    Map.Entry<String, Float> Entry = sortedOnSprinklrMethod.get(i);
                    value = Entry.getKey();
                }
                writeParam(MethodTemplate,variable,value);
                variable = "$JX_PE";
                value = "N/A";
                if(i<sortedOnSprinklrMethod.size()) {
                    Map.Entry<String, Float> Entry = sortedOnSprinklrMethod.get(i);
                    value = formatPercentage(Entry.getValue()/entry.getValue());
                }
                writeParam(MethodTemplate,variable,value);
            }
            System.out.println(MethodTemplate.toString());
        }
        List<Map.Entry<String, Float>> sortedEntriesTopSprinklr = TotalSprinklrCOM.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Float>comparingByValue().reversed())
                .toList();
        StringBuilder TopSprinklrTemplate = new StringBuilder(SprinklrTopMethodTemplate);
        for(int i = 0;i<5;i++){
            variable = "$HOT_TOP_COM_SPRINKLR";
            value = "N/A";
            if(i<sortedEntriesTopSprinklr.size()) {
                Map.Entry<String, Float> entry = sortedEntriesTopSprinklr.get(i);
                value = entry.getKey();
            }
            writeParam(TopSprinklrTemplate,variable,value);
            variable = "$KX_PE";
            value = "N/A";
            if(i<sortedEntriesTopSprinklr.size()) {
                Map.Entry<String, Float> entry = sortedEntriesTopSprinklr.get(i);
                value = formatPercentage(entry.getValue()/TotalExecutionEvent);
            }
            writeParam(TopSprinklrTemplate,variable,value);
        }
        System.out.println(TopSprinklrTemplate.toString());
    }
    public void Main(Path file){
        try (RecordingFile recordingFile = new RecordingFile(file)){ // Re-Reading the file
            while (recordingFile.hasMoreEvents()){
                RecordedEvent e = recordingFile.readEvent();
                String EventName = e.getEventType().getName();
                String CurrentFrame;
                if(EventName.equals("jdk.ExecutionSample")) {
                    try {
                        List<RecordedFrame> frames = e.getStackTrace().getFrames(); // getting all the frames from the Execution event.
                        if (!frames.isEmpty()) {
                            RecordedFrame topFrame = frames.get(0);
                            if(topFrame.isJavaFrame()) {
                                TotalExecutionEvent++;
                                String topMethod = formatMethod(topFrame.getMethod());
                                for (RecordedFrame frame : frames) {
                                    CurrentFrame = formatMethod(frame.getMethod());
                                    if(CurrentFrame.startsWith("com.spr")){
                                        TotalSprinklrCOM.merge(CurrentFrame,(float)1,Float::sum);
                                        if(MethodSet.containsKey(topMethod)){
                                            if(MethodWiseSprinklrCOM.containsKey(topMethod)){
                                                MethodWiseSprinklrCOM.get(topMethod).merge(CurrentFrame,(float)1,Float::sum);
                                            }
                                            else{
                                                MethodWiseSprinklrCOM.put(topMethod,new HashMap<>());
                                                MethodWiseSprinklrCOM.get(topMethod).put(CurrentFrame,(float)1);
                                            }
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
            printSprinklrStats();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
