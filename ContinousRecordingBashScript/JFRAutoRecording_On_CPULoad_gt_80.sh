#!/bin/bash

echo "Enter the <PID> of the JAVA process"
read PID
echo "Enter Complete path where you want the file to be dumped like /Users/harsh.kumar/Desktop - Only this format will work"
read path
SpecifiedPath="$path/recording3.jfr" #Path input can also be taken for users use case
echo "Path is: $SpecifiedPath"
NumberOfCores=$(getconf _NPROCESSORS_ONLN) # To get the number of Cores in the CPU
NameProcess=$(ps -p $PID -o comm=)
ProcessName=${NameProcess:(-4)} # Now we have retirived the name of the process 
Java="java"
if [[ $ProcessName == $Java ]]
then
    CPULoad=$(ps -p $PID -o %cpu=)
    CPULoad=$(printf "%.0f" "$CPULoad")
    RealCPU=$(expr $CPULoad / $NumberOfCores)
    # Now check if JFR recording is already running or not
    echo "Checking if JFR recording exists or not"
    echo "Sleeping for 10s as sometimes we may encounter IOException"
    sleep 10
    RecorderChecking=$(jcmd $PID JFR.check)
    Word="$(echo $RecorderChecking | awk '{print $2}')"
    if [[ "$Word" == "No" ]]
    then 
        echo "No Continous recording found, starting a new one"
        jcmd $PID JFR.start settings=profile
        echo "Sleeping for 100 seconds as we have just started the recording"
        sleep 100
    else  echo "It exists: "
    fi
    echo "The recording will only dump when the CPULoad >= 80%"
    while true
    do
        if [ $RealCPU -gt 80 ]
        then
            sleep 10
            echo "Recording file name = recording3"
            jcmd $PID JFR.dump name=1 filename=$SpecifiedPath
            echo "JFR DUMP SUCCESFULLY ON CPU LOAD > 80%"
            echo "Now we want this bash application to sit idle for more than 10 minutes as else we would just keep dumping"
            sleep 600
        fi
    done
else
    echo "Specidfied process $PID is not a java process"
fi