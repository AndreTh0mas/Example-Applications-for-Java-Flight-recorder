package org.example;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import jdk.jfr.consumer.EventStream;

import java.nio.file.Paths;
import java.util.Optional;
import java.util.Properties;

/*

How To run: Run the SleepOneSecondInterval.java first with flight recording ON:
```java -XX:StartFlightRecording=duration=100s,filename=MyRecording.jfr BusySleepInterval.java```
After this, run this file as java StreamExternalEventAttachAPISample.java

*/

public class StreamExternalEventAttachAPISample {
    /*
     StreamExternalEventsWithAttachAPISample uses the Attach API to obtain the virtual machine
     in which BusySleepInterval is running. From this virtual machine, StreamExternalEventsWithAttachAPISample
     obtains the location of its Flight Recorder repository though the jdk.jfr.repository property.
     It then creates an EventStream with this repository through the EventStream::openRepository(Paths) method.
     Where the openRepository method takes Path of repository of the target JVM.
     */
    public static void main(String[] args) throws Exception{
        Optional<VirtualMachineDescriptor> vmd =
                VirtualMachine.list().stream()
                        .filter(v -> v.displayName()
                                .contains("BusySleepInterval"))
                        .findFirst();
        if (vmd.isEmpty()) {
            throw new RuntimeException("Cannot find VM for BusySleepInterval");
        }
        VirtualMachine vm = VirtualMachine.attach(vmd.get());
        // Get system properties from attached VM
        Properties props = vm.getSystemProperties();
        String repository = props.getProperty("jdk.jfr.repository");
        System.out.println("jdk.jfr.repository: " + repository);
        try (EventStream stream = EventStream
                .openRepository(Paths.get(repository))) { // Get the path of the Flight recorder repository of the target JVM
            System.out.println("Found repository ...");
            stream.onEvent("jdk.ThreadSleep", System.out::println);
            stream.start(); // Blocking call
        }
    }
}
