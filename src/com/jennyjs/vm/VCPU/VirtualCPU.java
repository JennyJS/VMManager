package com.jennyjs.vm.VCPU;

import com.jennyjs.vm.Task.Task;
import com.jennyjs.vm.Util.FileResult;

/**
 * Created by jenny on 11/15/15.
 */
public class VirtualCPU {
    public int clusterId;
    final int vmId;
    final public int vCpuId;
    public int credit;
    public int weight;
    public Priority p;
    public Task task;
    //TODO
    public boolean isBusy;
    public long startProcessingIOTaskTime;


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


    public VirtualCPU(int vmId, int vCpuId, int weight){
        this.vmId = vmId;
        this.vCpuId = vCpuId;
        this.weight = weight;
    }

    public void loadTask(Task task){
        this.task = task;
        this.clusterId = task.groupID;
        this.isBusy = true;
        this.credit = Math.round(FileResult.grpCredits.get(this.clusterId-1) * this.weight /FileResult.VmsTotalVcpuWt[this.vmId]);
        this.p = Priority.under;
        System.out.println("Loading task " + task.taskID + " to vCPU " + this.vCpuId);
    }

    public void getVcpu()
    {
        System.out.print("Vcpu Id : "+ this.vCpuId + " Vm Id :"+this.vmId + " Group Id :"+this.clusterId);
        System.out.print(" Priority : "+ this.p + " Credit : " +this.credit + " Task id :" + this.task.taskID +"\n");
    }
}
