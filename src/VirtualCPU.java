/**
 * Created by jenny on 11/15/15.
 */
public class VirtualCPU {
    int clusterId;
    final int vmId;
    final int vCpuId;
    int weight;
    int cap;
    Priority p;
    private Task task;

    public enum Priority{    //Is it the priority of VMs same as priority of vCPUs
        idle,
        under,
        over,
        boost
    }

    public VirtualCPU(int clusterId, int vmId, int vCpuId, int weight, int cap, Priority p){
        this.clusterId = clusterId;
        this.vmId = vmId;
        this.vCpuId = vCpuId;
        this.weight = weight;
        this.cap = cap;
        this.p = p;
    }

    public void loadTask(Task task){
        this.task = task;
    }
}
