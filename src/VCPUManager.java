/**
 * Created by jenny on 11/15/15.
 */
public class VCPUManager {
    public void assignmentTasksToVCPU(){
        while (true){
            try {
                Task task = TaskQueue.getInstance().poll();
                VirtualCPU virtualCPU = VirtualCPUQueue.getInstance().poll();
                virtualCPU.loadTask(task);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
