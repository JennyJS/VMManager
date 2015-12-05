package com.jennyjs.vm;

import com.jennyjs.vm.PCPU.PCPUManager;
import com.jennyjs.vm.PCPU.PhysicalCPU;
import com.jennyjs.vm.Task.TaskGenerator;
import com.jennyjs.vm.Util.Constants;
import com.jennyjs.vm.Util.ReadFile;
import com.jennyjs.vm.Util.TaskInfo;
import com.jennyjs.vm.VCPU.VCPUManager;
import com.jennyjs.vm.VCPU.VirtualCPU;
import com.jennyjs.vm.VCPU.VCPUConnectorQueue;
import java.util.List;
import com.jennyjs.vm.Util.FileResult;

/**
 * Created by jenny on 11/14/15.
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {

        //generating tasks
            List<TaskInfo> list = ReadFile.ReadInputFile();
            Thread taskGeneratorThread = new Thread(new TaskGenerator(list));
            taskGeneratorThread.run();

            //create VCPUs and add to VCPUQueue
            for(int i = 0; i < FileResult.numVcpu; i++){
                VirtualCPU v = new VirtualCPU(1,i,1,2, VirtualCPU.Priority.under);
                VCPUConnectorQueue.getInstance().add(v);
            }

            //load task to VCPU, put the VCPUs into the run queue and sort based on MRG
            VCPUManager.getInstance(VCPUManager.ScheduleType.MRG).start();

            // create several PCPUs and put them into the PCPU queue
            for (int j = 0; j < Constants.PCPUNUMBER; j++){
                PCPUManager.getInstance().addPCUP(new PhysicalCPU(j, PhysicalCPU.Status.idle));
            }

            // allocate the vCPUs in the runQueue to the idle pCPUs
            PCPUManager.getInstance().run();
    }
}
