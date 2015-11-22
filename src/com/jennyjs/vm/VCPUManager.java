package com.jennyjs.vm;

import com.jennyjs.vm.ScheduleAlgorithm.CreditComparator;
import com.jennyjs.vm.ScheduleAlgorithm.MRGComparator;

/**
 * Created by jenny on 11/15/15.
 */
public class VCPUManager extends Thread {

    private final ScheduleType type;

    public enum ScheduleType {
        MRG,
        CREDIT
    }

    private static VCPUManager vcpuManager;
    public static VCPUManager getInstance(ScheduleType type){
        if (vcpuManager == null){
            vcpuManager = new VCPUManager(type);
        }
        return vcpuManager;
    }

    private VCPUManager(ScheduleType type){
        this.type = type;
    }

    @Override
    public void run() {
        while (true){
            try {
                Task task = TaskQueue.getInstance().poll();
                VirtualCPU virtualCPU = VirtualCPUQueue.getInstance().poll();
                virtualCPU.loadTask(task);
                if (type == ScheduleType.MRG){
                    MRGComparator mrgComparator = new MRGComparator();
                    Scheduler.getInstance(mrgComparator).addVcpu(virtualCPU);
                } else {
                    CreditComparator creditComparator = new CreditComparator();
                    Scheduler.getInstance(creditComparator).addVcpu(virtualCPU);
                }

            } catch (InterruptedException e){
                System.out.print(e.getMessage());
            }
        }
    }
}
