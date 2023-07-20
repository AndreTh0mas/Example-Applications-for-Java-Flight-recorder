package PrometheusGrafana;

public class Worker implements Runnable{

    public void run(){
        int x = 0;
        while(true){
            for(int i = 0;i<1000000;i++){
                x++;
                if(x%2 == 0){
                    x--;
                }
                else{
                    x--;
                }
            }
        }
    }
}
