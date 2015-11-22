package com.jennyjs.vm;

/**
 * Created by jenny on 11/21/15.
 */
public class PhysicalCPU extends Thread{

    final int pCPUId;
    Status s;
    int maxProcessingTime;
    private VirtualCPU virtualCPU;

    public enum Status{
        busy,
        idle
    }

    public PhysicalCPU(int pCPUId, Status status){
        this.pCPUId = pCPUId;
        this.s = status;
    }

    public void loadVCPU(VirtualCPU vCPU){
        this.virtualCPU = vCPU;
        this.s = Status.busy;
        System.out.println("Loading vCPU" + vCPU.vCpuId);
    }
}
