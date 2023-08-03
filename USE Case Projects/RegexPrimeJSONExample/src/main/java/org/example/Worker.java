package org.example;

public class Worker implements Runnable {
    public void run(){
        // I have chosen a Prime number such as to get most of the Computation time.
        PrimeCalculator primeCalculator  = new PrimeCalculator(123125671);
        LoggerEvent event = new LoggerEvent();
        RegexJSON RegexOperation = new RegexJSON();
        Thread Thread1 = new Thread(RegexOperation,"Inner Thread 1");
        Thread1.setDaemon(true);
        Thread1.start();
        int Count = 0;
        while(true){
            event.begin();
            event.WhichOperation = "Prime calculator";
            event.ThreadNumber = Thread.currentThread().getName();
            primeCalculator.run();
            event.commit();
            Count++;
            if(Count%10 == 0){
                Thread.yield();
            }

        }
    }

}
