package com.jennyjs.vm;

import com.jennyjs.vm.PCPU.PCPUManager;
import com.jennyjs.vm.PCPU.PhysicalCPU;
import com.jennyjs.vm.Task.TaskGenerator;
import com.jennyjs.vm.VCPU.VCPUManager;
import com.jennyjs.vm.VCPU.VirtualCPU;
import com.jennyjs.vm.VCPU.VCPUConnectorQueue;

/**
 * Created by jenny on 11/14/15.
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {

        //generating tasks
        Thread taskGeneratorThread = new Thread(new TaskGenerator(5));
        taskGeneratorThread.run();

        //create VCPUs and add to VCPUQueue
        for(int i = 0; i < 6; i++){
            VirtualCPU v = new VirtualCPU(1,1,i,1,2, VirtualCPU.Priority.under);
            VCPUConnectorQueue.getInstance().add(v);
        }

        //load task to VCPU, put the VCPUs into the run queue and sort based on MRG
        VCPUManager.getInstance(VCPUManager.ScheduleType.MRG).start();

        // create several PCPUs and put them into the PCPU queue
        for (int j = 0; j < 5; j++){
            PCPUManager.getInstance().addPCUP(new PhysicalCPU(j, PhysicalCPU.Status.idle));
        }

        // allocate the vCPUs in the runQueue to the idle pCPUs
        PCPUManager.getInstance().run();
    }
}
