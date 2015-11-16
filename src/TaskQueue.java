import java.util.Queue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by jenny on 11/14/15.
 */
public class TaskQueue<T> {
    final private LinkedBlockingDeque<Task> queue = new LinkedBlockingDeque<>(100);
    private static TaskQueue<Task> taskQueue;

    public static TaskQueue getInstance(){
        if (taskQueue == null) {
            taskQueue = new TaskQueue();
        }
        return taskQueue;
    }
    public void addTask(Task t){
        this.queue.add(t);
    }
    public Task poll() throws InterruptedException {
        return this.queue.take();
    }
}
