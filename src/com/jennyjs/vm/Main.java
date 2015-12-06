package com.jennyjs.vm;

import com.jennyjs.vm.PCPU.PCPUManager;
import com.jennyjs.vm.PCPU.PhysicalCPU;
import com.jennyjs.vm.Task.TaskGenerator;
import com.jennyjs.vm.Util.Constants;
import com.jennyjs.vm.Util.ParseResult;
import com.jennyjs.vm.Util.ReadFile;
import com.jennyjs.vm.VCPU.Dom0Manager;
import com.jennyjs.vm.VCPU.VCPUManager;
import com.jennyjs.vm.VCPU.VirtualCPU;

/**
 * Created by jenny on 11/14/15.
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        final ParseResult parseResult;
        try {
            parseResult = ReadFile.parseFile("/Users/jenny/Java_workspace/COEN283_P3/src/com/jennyjs/vm/input.json");
        } catch (Exception e) {
            System.out.println("Can't read file! Abort! " + e);
            return;
        }

        VirtualCPU.parseResult = parseResult;

        final Thread taskGeneratorThread = new Thread(new TaskGenerator(parseResult.tasks));
        taskGeneratorThread.run();

        //load task to VCPU, put the VCPUs into the run queue and sort based on MRG
        for (VirtualCPU virtualCPU : parseResult.virtualCPUs) {
            VCPUManager.VCPUConnectorQueue.getInstance().add(virtualCPU);
        }

        VCPUManager.init(VCPUManager.ScheduleType.MRG);
        Dom0Manager.getInstance().start();
        VCPUManager.getInstance().start();

        // create several PCPUs and put them into the PCPU queue
        for (int j = 0; j < Constants.PCPU_NUMBER; j++){
            PCPUManager.getInstance().addPCUP(new PhysicalCPU(j));
        }

        // allocate the vCPUs in the runQueue to the idle pCPUs
        PCPUManager.getInstance().run();
    }
}
