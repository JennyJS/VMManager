/**
 * Created by jenny on 11/14/15.
 */
public class TaskGenerator implements Runnable{
    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Task t = new Task();
            TaskQueue.getInstance().addTask(t);
        }

    }
}
