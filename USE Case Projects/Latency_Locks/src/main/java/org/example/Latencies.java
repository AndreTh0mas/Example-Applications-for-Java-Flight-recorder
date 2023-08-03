package org.example;

import java.io.IOException;


public class Latencies {
	public static void main(String[] args) throws InterruptedException, IOException {
		ThreadGroup threadGroup = new ThreadGroup("Workers");
		Thread.sleep(2000);
		Thread[] threads;
		threads = new Thread[20];
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(threadGroup, new Worker(i, 30_000_000), "Worker Thread " + i);
			threads[i].setDaemon(true);
			System.out.println("Starting " + threads[i].getName() + "...");
			threads[i].start();
		}
		System.out.println("Application is running: Launch flight recording to analyse on JMC");
		System.out.println("Press <enter> to quit!");
		System.out.flush();
		System.in.read();
	}
}
