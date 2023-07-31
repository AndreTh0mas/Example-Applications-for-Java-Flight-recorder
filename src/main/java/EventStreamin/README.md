
# Java Flight Recorder Event Streaming API Examples
This package contains a collection of examples demonstrating the usage of the Java Flight Recorder (JFR) Event Streaming API. The Event Streaming API allows you to programmatically interact with JFR to create custom events, record data remotely, manage settings, and perform active and passive recording. These examples showcase the versatility and power of the Event Streaming API, enabling you to harness the full potential of JFR in your Java applications.

## Some of the examples
####  Example 1: Working on Custom Events
This example demonstrates how to define and create custom events. Setting ``@Threshold`` on those events, using ``@SettingDefinition`` to only commit events which statidfies specified condition.

####  Example 2: Remote Recording Stream
Initiating remote recording sessions using the Event Streaming API connection via JMX.
####  Example 3: Starting EventStreaming using PID|MainClass
In this example we are starting the recording stream using PID of the running process or using its MainClass name.

####  Example 4: Active and Passive Recording
This example demonstrates the difference between active and passive recording modes of recording stream.

## Usage
Each example has its standalone file and includes comments to navigate on how to use the program and what to look for.
## Requirements
For EventStreaming API: ``Jdk14 and above``.  
For RemoteRecordingStream:  ``Jdk16 and above``. 
