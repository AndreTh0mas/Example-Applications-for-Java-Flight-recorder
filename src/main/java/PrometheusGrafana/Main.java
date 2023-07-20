package PrometheusGrafana;
import java.io.IOException;

public class Main {
    // Here we are going to make a application where we are going to create simple custom events and then send the data
    // of those custom events to monitoring tools such as Prometheus and Grafana
    // First simple application
    static int NUMBER_OF_THREADS = 40;
    public static void main(String[] args) throws IOException {
//        String ExampleString= "ExampleString";
//        ThreadGroup threadGroup = new ThreadGroup("Workers");
//        Thread[] threads = new Thread[NUMBER_OF_THREADS];
//        for (int i = 0; i < threads.length; i++) {
//            String RequestBody;
//            if(i%2 == 0){
//                RequestBody = ExampleString;
//            }
//            else{
//                RequestBody = "ThreadID"+ i;
//            }
//            threads[i] = new Thread(threadGroup,new WorkerNew(RequestBody) , "Worker Thread " + i);
//            threads[i].setDaemon(true);
//            threads[i].start();
//        }
//        System.out.print("Press enter to quit!");
//        System.out.flush();
//        System.in.read();
        ThreadGroup threadGroup = new ThreadGroup("Workers");
        Thread[] threads = new Thread[NUMBER_OF_THREADS];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(threadGroup,new Worker() , "Worker Thread " + i);
            threads[i].setDaemon(true);
            threads[i].start();
        }
        System.out.print("Press enter to quit!");
        System.out.flush();
        System.in.read();
    }

}
