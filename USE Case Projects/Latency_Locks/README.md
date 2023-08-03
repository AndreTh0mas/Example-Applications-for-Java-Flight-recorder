# Java Blocking Detection and Analysis
This Java application demonstrates a real-world use case of detecting Java blocking scenarios and showcases how to detect and analyze them using Java Flight Recorder (JFR) and Java Mission Control (JMC).
With JFR and JMC, we can efficiently identify and optimize these areas to enhance the overall application performance.

## How to Use

After Building the project. Run the ``Latencies.java`` in this package.
Then use the command below to know the PID of the process
```bash
jcmd
```
After that, use:
```bash
jcmd <PID of your process> JFR.start settings=profile duration=100s filename=<PATH>
```
To create your own flight recording. After the duration of the recording -> to allow the application to run for a sufficient duration to capture meaningful profiling data, a JFR recording file (.jfr) will be generated. Open this file using Java Mission Control (JMC) to analyze the recorded data.
## Features
* **Blocking Simulation**: The application will intentionally introduce blocking scenarios to mimic real-world scenarios where threads may block, causing performance degradation.

* **Java Flight Recorder (JFR)**: JFR will be used to record events related to thread blocking, such as thread sleep, I/O operations, or synchronization waits.

* **Java Mission Control (JMC)**: JMC will be utilized to analyze the recorded JFR data and gain detailed insights into the application's thread behavior, thread contention, and overall performance.
* **Example Recording**: I have included JFR recording of ``before & after (.jfr) files`` fixing the issue which is causing the problem. Use those recordings to analyze and observe the effects under the **Lock Instances TAB**.

## Walk Through

1. Look at the ``Latency_before.jfr`` recording using JMC.
2. Go to the **Lock Instances** page. Observe which Threads are blocked most of the time & on which Monitor.
3. The blocking events all seem to be due to calls to the **log method**.
4. We want the worker threads to be able to do logging in parallel.
5. We should remove the ``synchronization`` keyword from the **log method**.
