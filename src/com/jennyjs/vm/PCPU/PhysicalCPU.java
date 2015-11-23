package com.jennyjs.vm.PCPU;

import com.jennyjs.vm.ScheduleAlgorithm.Scheduler;
import com.jennyjs.vm.VCPU.VirtualCPU;

/**
 * Created by jenny on 11/21/15.
 */
public class PhysicalCPU implements Runnable{

    final int pCPUId;
    public Status s;
    int maxProcessingTime;
    private VirtualCPU virtualCPU;

    @Override
    public void run() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        virtualCPU.task.increasTime(1);

        if (virtualCPU.task.isFinished()){
            virtualCPU.p = VirtualCPU.Priority.idle;
            virtualCPU.task = null;
            System.out.println("Task " + virtualCPU.task.taskID + " is finished.");
        }

        // VCPU back to queue
        Scheduler.getInstance().addVcpu(virtualCPU);
        PCPUManager.getInstance().addPCUP(this);
    }

    public enum Status{
        busy(0),
        idle(1);
        private int level;
        Status(int l){
            this.level = l;
        }

        public int pCPULevel(){
            return level;
        }
    }

    public PhysicalCPU(int pCPUId, Status status){
        this.pCPUId = pCPUId;
        this.s = status;
    }

    public void loadVCPU(VirtualCPU vCPU){
        this.virtualCPU = vCPU;
        this.s = Status.busy;
        System.out.println("Loading vCPU " + vCPU.vCpuId);
    }

    @Override
    public String toString(){
        return "PCPU Id is " + this.pCPUId;
    }
}
