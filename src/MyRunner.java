/**
 * Created by jenny on 11/14/15.
 */
public class MyRunner {

    public static void main(String[] args) throws InterruptedException {
        Thread taskGeneratorThread = new Thread(new TaskGenerator(10));
        taskGeneratorThread.start();
        while (true){
            try {
                System.out.println("Polling.." + TaskQueue.getInstance().poll());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
