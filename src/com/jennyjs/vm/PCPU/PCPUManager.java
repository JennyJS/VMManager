package com.jennyjs.vm.PCPU;


import com.jennyjs.vm.VCPU.VirtualCPU;
import com.jennyjs.vm.ScheduleAlgorithm.MRGComparator;
import com.jennyjs.vm.ScheduleAlgorithm.PCPUComparator;
import com.jennyjs.vm.ScheduleAlgorithm.Scheduler;

import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;


/**
 * Created by jenny on 11/22/15.
 */
public class PCPUManager implements Runnable {
    private final PriorityBlockingQueue<PhysicalCPU> pCPUQueue;
    public void addPCUP(PhysicalCPU physicalCPU){
        pCPUQueue.add(physicalCPU);
    }

    private PCPUManager(Comparator<PhysicalCPU> comparator){
        pCPUQueue = new PriorityBlockingQueue<>(5, comparator);
    }


    private static PCPUManager pcpuManager;
    public static PCPUManager getInstance(){
        if (pcpuManager == null){
            pcpuManager = new PCPUManager(new PCPUComparator());
        }
        return pcpuManager;
    }


    @Override
    public void run() {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        while(true){
            MRGComparator mrgComparator = new MRGComparator();
            try {
                VirtualCPU vCPU = Scheduler.getInstance(mrgComparator).pollVcpu();
                PhysicalCPU pCPU = pCPUQueue.take();
                pCPU.loadVCPU(vCPU);
                executor.submit(pCPU);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
