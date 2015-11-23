package com.jennyjs.vm.ScheduleAlgorithm;

import com.jennyjs.vm.VCPU.VirtualCPU;

import java.util.Comparator;
import java.util.concurrent.*;

/**
 * Created by jenny on 11/21/15.
 */
public class Scheduler {
    private final PriorityBlockingQueue<VirtualCPU> runQueue;
    private static Scheduler Scheduler;

    private Scheduler(Comparator<VirtualCPU> comparator){
        runQueue = new PriorityBlockingQueue<>(10, comparator);
    }

    public static Scheduler getInstance(Comparator<VirtualCPU> comparator){
        if (Scheduler == null){
            Scheduler = new Scheduler(comparator);
        }
        return Scheduler;
    }

    public void addVcpu(VirtualCPU virtualCPU){
        this.runQueue.add(virtualCPU);
    }

    public VirtualCPU pollVcpu () throws InterruptedException {
        return runQueue.take();
    }

}
