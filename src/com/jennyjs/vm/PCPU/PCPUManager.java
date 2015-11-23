package com.jennyjs.vm.PCPU;


import com.jennyjs.vm.VCPU.VirtualCPU;
import com.jennyjs.vm.ScheduleAlgorithm.PCPUComparator;
import com.jennyjs.vm.ScheduleAlgorithm.VCPUScheduler;

import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;


/**
 * Created by jenny on 11/22/15.
 */
public class PCPUManager implements Runnable {

    private final PriorityBlockingQueue<PhysicalCPU> pCPUQueue;

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

    public void addPCUP(PhysicalCPU physicalCPU){
        System.out.println("Adding PCPU back to queue " + physicalCPU.toString());
        pCPUQueue.add(physicalCPU);
    }

    @Override
    public void run() {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        while(true){
            try {
                VirtualCPU vCPU = VCPUScheduler.getInstance().pollVcpu();
                PhysicalCPU pCPU = pCPUQueue.take();
                pCPU.loadVCPU(vCPU);
                executor.submit(pCPU);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
