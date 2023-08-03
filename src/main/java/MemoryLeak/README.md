# Memory Leak Detection and Analysis
This Java application demonstrates a scenario of memory leak and showcases how to detect and analyze memory leaks using Java Flight Recorder (JFR) and Java Mission Control (JMC).
## How to Use

After Building the project. Run:  
```bash
java MemoryLeak.java
```
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
Memory leaks in Java applications can lead to increased memory consumption, degradation in application performance, and even OutOfMemoryError. This Java application simulates a memory leak scenario to showcase the identification and analysis of memory leaks using JFR and **JDK Mission Control**.
For example purposes i have included JFR recording of ``before & after (.jfr) files`` fixing the issue which is causing the problem. Use those recordings to analyze and observe the effects under the **Memory** & **Live Objects** Tabs.

## Walk Through

1. Look at the MemoryLeak_before.jfr recording.
2. Go to the **Live Objects** page under **Memory Tab** in JMC. Observe which objects remain to be there forever.
3. It uses ``Old object event`` to capture information.
