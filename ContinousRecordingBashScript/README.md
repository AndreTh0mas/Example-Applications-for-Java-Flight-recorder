# JFR Recording Dump on High CPU Usage
This Bash script automates the process of starting Continous Java Flight Recorder (JFR) recording and Dumping it upon Trigger. It monitors the CPU usage of the application, and if it exceeds 80%, it dumps the JFR recording for later analysis to a specified file path.

## Usage
Make the script executable:
```bash
  chmod +x JFRAutoRecording_On_CPULoad_gt_80.sh
```

In root directory of the Script: Run
```bash
  ./JFRAutoRecording_On_CPULoad_gt_80.sh
```

Provide ``PID``of the Java process & ``Path where to Dump the recording`` when asked by the Script.
## Description
This script performs the following actions:

1. Show all the running Java processes.
2. Starts JFR recording for the specified PID using jcmd.
3. Monitors the CPU usage of the Java application using top in batch mode.
4. If the **CPU usage exceeds 80%**, it Dumps the JFR recording and saves it to a file for later analysis.
5. The JFR recording file will be saved with a timestamp in the format ``JFR_PID_TIMESTAMP.jfr``.
## Script Behavior
* If the specified PID does not correspond to a running Java application or the specified path does not exists, the script will exit with an error message.
* The script will continuously monitor the CPU usage of the specified Java application until it exceeds 80% or you interrupt the script manually (CTRL+C).

## Example


![Example on Regex Prime application](https://github.com/AndreTh0mas/Java-Flight-Recorder-Showcase/blob/main/ContinousRecordingBashScript/BashScriptExample.png)




## Requirements
Bash (Bourne Again SHell) should be installed on your system.  
Java Development Kit (JDK) with Java Flight Recorder (JFR) support (**Jdk** above 8)