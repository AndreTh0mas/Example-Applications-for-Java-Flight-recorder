package PrometheusGrafana;

public class WorkerNew implements Runnable{
    public String RequestBody;
    public WorkerNew(String RequestBody){
        this.RequestBody = RequestBody;
    }
    public void run(){
        while(true){
            GetRequest getRequest = new GetRequest();
            PostRequest postRequest = new PostRequest();
            postRequest.setInput(RequestBody);
            try{
                // PostRequest with Thread.sleep method
                String Response = postRequest.Response(); // Just invoking both of the Requests
            }
            catch(Exception e){
                return;
            }
            // GetRequest
            String Response = getRequest.Response();

        }
    }

}
