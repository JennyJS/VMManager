package com.jennyjs.vm.VCPU;

import com.jennyjs.vm.ScheduleAlgorithm.CreditComparator;
import com.jennyjs.vm.ScheduleAlgorithm.MRGComparator;
import com.jennyjs.vm.ScheduleAlgorithm.VCPUScheduler;
import com.jennyjs.vm.Task.Task;
import com.jennyjs.vm.Task.TaskQueue;

import java.util.Comparator;

/**
 * Created by jenny on 11/15/15.
 */
public class VCPUManager extends Thread {

    private final ScheduleType type;

    public enum ScheduleType {
        MRG(new MRGComparator()),
        CREDIT(new CreditComparator());

        private Comparator<VirtualCPU> comparator;

        ScheduleType(Comparator<VirtualCPU> comparator){
            this.comparator = comparator;
        }

        public Comparator<VirtualCPU> getComparator(){
            return this.comparator;
        }
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
        VCPUScheduler.init(type.getComparator());
    }

    @Override
    public void run() {

        while (true){
            try {
                Task task = TaskQueue.getInstance().poll();
                VirtualCPU virtualCPU = VCPUConnectorQueue.getInstance().poll();
                virtualCPU.loadTask(task);
                VCPUScheduler.getInstance().addVcpu(virtualCPU);
            } catch (InterruptedException e){
                System.out.print(e.getMessage());
            }
        }
    }
}
