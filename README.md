# JFR in Industry: Use Cases and Automation

## Overview

This project focuses on Java Flight Recorder (JFR) and its applications in industry for performance analysis and optimization. The project includes several use case applications designed to demonstrate various aspects of JFR's capabilities, such as GC analysis, HotMethods analysis, Memory Leak detection, and Latency application on lock contention. Additionally, the project explores the Event Streaming API for real-time event processing. To enhance usability and efficiency, a Bash script has been developed to automate the JFR dumping process.

## Contents

1. ``Use Case Applications``
    - **GC Analysis**: A Java application that simulates garbage collection scenarios to analyze memory usage and efficiency.
    - **HotMethods Analysis**: An application to identify frequently executed methods to optimize performance.
    - **Memory Leak Detection**: A use case to detect memory leaks and analyze their impact on application resources.
    - **Latency Application on Lock Contention**: An application that measures the impact of lock contention on latency and performance.

2. ``Bash Script for JFR Dumping Automation``
    - A Bash script that automates the JFR recording and dumping process. The script ensures efficient data collection and analysis.
3. ``Event Streaming API Exploration``: 
    - An exploration of the Event Streaming API for real-time event processing and analysis.

## Getting Started

Clone the project repository:
```bash
git clone https://github.com/AndreTh0mas/Java-Flight-Recorder-Showcase.git
cd Java-Flight-Recorder-Showcase
```
To use each project, navigate to its respective directory. For Use case application navigate to ``USE Case Projects directory`` to find all the projects there.
## Overview
Each use case application has its own README file with instructions on how to use it. Open any project directory of your choice and follow the instructions provided in the README to test and explore the specific use case application.

In these directories, you will find detailed information on how to build, run, and analyze the Java applications related to GC analysis, HotMethods analysis, Memory Leak detection, Latency application on lock contention, and the Event Streaming API exploration. The README files will guide you through the steps to execute each use case and leverage Java Flight Recorder (JFR) and Java Mission Control (JMC) for performance analysis and optimization.

## Mentions
Marcus Hirt JMC Tutorial.

## Requirements
Jdk version 11 & above  
Jdk Misson Control

