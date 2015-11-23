package com.jennyjs.vm.ScheduleAlgorithm;

import com.jennyjs.vm.VCPU.VirtualCPU;

import java.util.Comparator;
import java.util.concurrent.*;

/**
 * Created by jenny on 11/21/15.
 */
public class Scheduler {
    private final PriorityBlockingQueue<VirtualCPU> runQueue;
    private static Scheduler scheduler;

    private Scheduler(Comparator<VirtualCPU> comparator){
        runQueue = new PriorityBlockingQueue<>(10, comparator);
    }

    public static void init(Comparator<VirtualCPU> comparator) {
        scheduler = new Scheduler(comparator);
    }

    public static Scheduler getInstance(){
        if (scheduler == null){
            throw new IllegalArgumentException("Scheduler hasn't been initialized.");
        }
        return scheduler;
    }

    public void addVcpu(VirtualCPU virtualCPU){
        this.runQueue.add(virtualCPU);
    }

    public VirtualCPU pollVcpu () throws InterruptedException {
        return runQueue.take();
    }

}
