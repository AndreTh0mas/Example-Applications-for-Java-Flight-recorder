#!/bin/bash

echo "--------- Running JAVA Processes ---------"
jps
echo "Enter the <PID> of the JAVA process for recording"
read PID
NumberOfCores=$(getconf _NPROCESSORS_ONLN) # To get the number of Cores in the CPU
NameProcess=$(ps -p $PID -o comm=)
ProcessName=${NameProcess:(-4)} # Now we have retirived the name of the process
Java="java"
if [[ $ProcessName == $Java ]]
then
    echo "Enter Complete path where you want the file to be dumped like /Users/harsh.kumar/Desktop - Only this format will work"
    read path
    # Now check if the specified path exists or not.
    if [ -e "$path" ];
    then
        echo " Specified Path exists."
    else
        echo "Specified Path does not exist. Example path -> /Users/harsh.kumar/Desktop"
        exit 1
    fi
    current_timestamp=$(date "+%Y-%m-%d-%H:%M:%S")
    filename="JFR_${PID}_${current_timestamp}.jfr"
    SpecifiedPath="$path/$filename" #Path input can also be taken for users use case
    echo "File Path is: $SpecifiedPath"
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
            echo "Recording file name = $filename"
            jcmd $PID JFR.dump name=1 filename=$SpecifiedPath
            echo "JFR DUMP SUCCESFULLY ON CPU LOAD > 80%"
            echo "Now we want this bash application to sit idle for more than 10 minutes as else we would just keep on dumping"
            sleep 600
        fi
    done
else
    echo "Specidfied process $PID is not a java process"
fi

