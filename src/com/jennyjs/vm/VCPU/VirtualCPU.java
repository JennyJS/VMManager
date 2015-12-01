package com.jennyjs.vm.VCPU;

import com.jennyjs.vm.Task.Task;

/**
 * Created by jenny on 11/15/15.
 */
public class VirtualCPU {
    public int clusterId;
    final int vmId;
    final public int vCpuId;
    int credit;
    public Priority p;
    public Task task;
    public boolean isDom0;
    //TODO
    public boolean isBusy;

    public enum Priority{    //when to change the Priority?
        under(1),
        over(2);

        private int level;
        Priority(int i) {
            this.level = i;
        }

        public int priorityLevel(){
            return level;
        }
    }


    public VirtualCPU(int clusterId, int vmId, int vCpuId, int credit, Priority p){
        this.clusterId = clusterId;
        this.vmId = vmId;
        this.vCpuId = vCpuId;
        this.credit = credit;
        this.p = p;
    }

    public void loadTask(Task task){
        this.task = task;
        this.isBusy = true;
        System.out.println("Loading task " + task.taskID + " to vCPU " + this.vCpuId);
    }
}
