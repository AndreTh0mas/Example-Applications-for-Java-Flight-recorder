
# Performance Cost of Autoboxing

This Java application demonstrates the performance cost of autoboxing in Java using Java Flight Recorder (JFR). Autoboxing is a feature in Java that automatically converts primitive data types into their corresponding wrapper classes (e.g., int to Integer) when needed. While this feature provides convenience, it can have performance implications, especially in critical code paths and loops.

## How to Use

After Building the project. Run the Main file of this package.
Then use the command below to know the PID of this process
```bash
jcmd
```
After that use:
```bash
jcmm <PID of your process> JFR.start settings=profile duration=100s filename=<PATH>
```
To create your own flight recording. After the duration of the recording, a JFR recording file (.jfr) will be generated. Open this file using Java Mission Control (JMC) analyze the recorded data.

## Overview

The purpose of this application is to showcase the impact of autoboxing on application performance it shows allocation behavior due to a suboptimal choice in datatypes. It measures the ``Garbage Collection`` behaviour of application with and without autoboxing enabled. By using Java Flight Recorder, we can capture detailed profiling information and analyze the performance characteristics using tools such as **JDK Mission Control**.
For example purposes i have included JFR recording with and without relying on autoboxing. Use those recordings to analyze and observe the effects in the **Garbage collection Tab**.

## Walk Through

1. Look at the allocation_before.jfr recording.
2. Go to the Garbage Collections page. Observe at the frequency of garbage collections.
3. Look at the Memory page and observe the most allocated objects.
4. Memory tab helps. TLAB page shows even more detail.
5. Allocator Autoboxes int to Integer, causing the allocations.
5. Typically, when primitive types are used as index in HashMaps, storing the object version is a better idea than
   going back and forth between the primitive type and Object version.
6. After fixing, there are no garbage collections anymore. Check and compare Garbage Collections pages for the two recordings. 

