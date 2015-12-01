package com.jennyjs.vm.PCPU;

import com.jennyjs.vm.ScheduleAlgorithm.VCPUScheduler;
import com.jennyjs.vm.Util.Constants;
import com.jennyjs.vm.VCPU.VirtualCPU;

/**
 * Created by jenny on 11/21/15.
 */
public class PhysicalCPU implements Runnable{

    final int pCPUId;
    public Status status;

    private VirtualCPU virtualCPU;

    @Override
    public void run() {
        try {
            Thread.sleep(Constants.MAXPCPUPROCESSINGTIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        virtualCPU.task.calculateExecutedTime(Constants.MAXPCPUPROCESSINGTIME);

        if (virtualCPU.task.isFinished()){
            virtualCPU.isBusy = false;
            virtualCPU.task = null;
            System.out.println("Task " + virtualCPU.task.taskID + " is finished.");
        }

        // VCPU back to queue
        VCPUScheduler.getInstance().addVcpu(virtualCPU);
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
        this.status = status;
    }

    public void loadVCPU(VirtualCPU vCPU){
        this.virtualCPU = vCPU;
        this.status = Status.busy;
        System.out.println("Loading vCPU " + vCPU.vCpuId);
    }

    @Override
    public String toString(){
        return "PCPU Id is " + this.pCPUId;
    }
}
