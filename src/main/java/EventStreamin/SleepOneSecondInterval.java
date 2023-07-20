package EventStreamin;

public class SleepOneSecondInterval {
    public static void main(String[] args) throws Exception{
        long pid = ProcessHandle.current().pid();
        System.out.println("Process PID: "+ pid);
        while(true){
            System.out.println("Sleeping for 1second");
            Thread.sleep(1000);
        }
    }
}
