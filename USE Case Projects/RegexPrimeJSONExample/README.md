# Java Application with Regex, Prime, and JSON Operations
This Java application showcases three essential operations: Regex, Prime, and JSON handling. It provides functionalities to perform regular expression operations, check for prime numbers, and convert Java objects to JSON and vice versa.  Additionally, the application is designed to be analyzed using Java Flight Recorder (JFR) and Java Mission Control (JMC) to gain insights into its performance and behavior.

## How to Use

After Building the project. Run: ```Main.java```
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
1. **Regex Operations**: The application demonstrates regex operations on large input strings, leading to High CPU Usage conditions.

2. **Prime Number Operations**: Similarly, prime number validation is a CPU-intensive task if not done efficiently.

3. **JSON Conversion**: The application also converts Java objects to JSON format and vice versa, which is another CPU-intensive operation.

These examples serve as a guide to understanding how to identify Hot Methods in the application using JFR & JMC. By locating which operations cause performance bottlenecks through Custom events, monitoring High CPU Usage Threads, and examining their Stack traces, users can gain insights into the areas that require optimization.

## Objective
The main objective of this application is to provide a practical demonstration of how to analyze the application's performance using JFR & JMC. By using these tools, developers can identify performance bottlenecks, optimize code execution, and gain a deeper understanding of the application's behavior under various scenarios. Through the provided examples of regex, prime number operations, and JSON conversion, users can explore the tools' capabilities and apply them to their own projects for effective performance analysis and optimization