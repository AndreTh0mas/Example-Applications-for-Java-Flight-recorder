# HotMethods Detection and Analysis
This Java application demonstrates a real-world use case of detecting HotMethods using Java Flight Recorder (JFR) and analyzing the recorded data on Java Mission Control (JMC). HotMethods are critical code paths that consume significant CPU time and can become performance bottlenecks in Java applications. With JFR and JMC, we can efficiently identify and optimize these HotMethods to enhance the overall application performance.

## How to Use

After Building the project. Run the ```HotMethods.java``` of this package.
Then use the command below to know the PID of the process
```bash
jcmd
```
After that, use:
```bash
jcmd <PID of your process> JFR.start settings=profile duration=100s filename=<PATH>
```
To create your own flight recording. After the duration of the recording -> to allow the application to run for a sufficient duration to capture meaningful profiling data, a JFR recording file (.jfr) will be generated. Open this file using Java Mission Control (JMC) to analyze the recorded data.

## Overview

The provided Java application is designed to simulate a real-world scenario where HotMethods may exist in a complex Java application. It consists of performance-critical code sections that could potentially become HotMethods. We use JFR to record data during the application's execution to capture the HotMethods.
By using Java Flight Recorder, we can capture detailed profiling information and analyze the performance characteristics using tools such as **JDK Mission Control**.
For example purposes i have included JFR recording of ``before & after (.jfr) files`` fixing the issue which is causing the problem. Use those recordings to analyze and observe the effects under the **Method Profilling Tab**.

## Walk Through

1. Look at the HotMethods_before.jfr recording.
2. Go to the **Method Profilling** page. Observe which methods are responsible for most of the CPU Usage.
3. We are spending a lot of time in the **LinkedList.equals(Object)/LinkedList.indexOf(Object) method**.
4. Memory tab helps. TLAB page shows even more detail.
5. Looking at the trace to the hottest method, shows that we are passing through the contains method.
6. Contains in a linked list is proportional to the number of entries ``O(n)``.
7. A HashSet should be used here.
