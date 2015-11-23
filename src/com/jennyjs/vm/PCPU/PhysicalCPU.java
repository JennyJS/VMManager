package com.jennyjs.vm.PCPU;

import com.jennyjs.vm.VCPU.VirtualCPU;
import com.jennyjs.vm.VCPU.VirtualCPUQueue;

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
        virtualCPU.task.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        virtualCPU.task.stop();

        if (virtualCPU.task.isFinished()){
            virtualCPU.p = VirtualCPU.Priority.idle;
            System.out.println("Task " + virtualCPU.task.taskID + " is finished.");
        }

        // VCPU back to queue
        VirtualCPUQueue.getInstance().add(virtualCPU);
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
}
