package com.jennyjs.vm;

/**
 * Created by jenny on 11/15/15.
 */
public class VirtualCPU {
    public int clusterId;
    final int vmId;
    final int vCpuId;
    int weight;
    int cap;
    public Priority p;
    private Task task;

    public enum Priority{    //Is it the priority of VMs same as priority of vCPUs
        idle (0),
        under(1),
        over(2),
        boost(3);

        private int level;
        Priority(int i) {
            this.level = i;
        }

        public int priorityLevel(){
            return level;
        }
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
        System.out.println("Loading task " + task.taskID + " to vCPU " + this.vCpuId);
    }
}
