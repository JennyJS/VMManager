/**
 * Created by jenny on 11/14/15.
 */
public class TaskGenerator implements Runnable {
    private final int taskNum;

    public TaskGenerator(int taskNum) {
        this.taskNum = taskNum;
    }

    @Override
    public void run() {
        int taskID = 0;
        while (taskID < taskNum) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Task t = new Task(10, Task.TaskType.NonIOTask, taskID++, 1);
            TaskQueue.getInstance().addTask(t);
            System.out.println("Generating..." + t);
        }
    }
}
