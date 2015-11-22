package com.jennyjs.vm;

/**
 * Created by jenny on 11/14/15.
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        Thread taskGeneratorThread = new Thread(new TaskGenerator(5));
        taskGeneratorThread.run();
        for(int i = 0; i < 6; i++){
            VirtualCPU v = new VirtualCPU(1,1,i,1,2, VirtualCPU.Priority.under);
            VirtualCPUQueue.getInstance().add(v);
        }
        VCPUManager.getInstance(VCPUManager.ScheduleType.MRG).start();

        for (int j = 0; j < 5; j++){
            PCPUManager.getInstance().addPCUP(new PhysicalCPU(j, PhysicalCPU.Status.idle));
        }
        // allocate the vCPUs in the runQueue to the idle pCPUs
        PCPUManager.getInstance().run();
    }
}
