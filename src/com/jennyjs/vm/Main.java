package com.jennyjs.vm;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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


        // allocate the vCPUs in the runQueue to the idle pCPUs
        PCPUManager.getInstance().run();
    }
}
