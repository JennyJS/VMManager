import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by jenny on 11/14/15.
 */
public class TaskQueue<T> {
    Queue<Task> queue = new ConcurrentLinkedDeque<>();
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
    public Task poll() {
        return this.queue.poll();
    }
}
