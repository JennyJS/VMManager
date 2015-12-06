package com.jennyjs.vm.PCPU;


import com.jennyjs.vm.Task.Task;
import com.jennyjs.vm.Util.Constants;
import com.jennyjs.vm.VCPU.Dom0Queue;
import com.jennyjs.vm.VCPU.VirtualCPU;
import com.jennyjs.vm.ScheduleAlgorithm.PCPUComparator;
import com.jennyjs.vm.ScheduleAlgorithm.VCPUScheduler;

import java.util.Calendar;
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
        ExecutorService executor = Executors.newFixedThreadPool(Constants.PCPUNUMBER);
        //pool from dom0 queue first and check, then poll from VCPU queue
        while(true){
            try {
                VirtualCPU virtualCPUInDom0 = Dom0Queue.getInstance().peek();
                VirtualCPU vCPU;
                if (virtualCPUInDom0 != null && System.currentTimeMillis() - virtualCPUInDom0.startProcessingIOTaskTime >= Constants.IOTASKPROCESSINGTIME){
                    vCPU = virtualCPUInDom0;
                } else {
                    vCPU = VCPUScheduler.getInstance().pollVcpu();
                    while (vCPU.task.taskType == Task.TaskType.IoTask){
                        Dom0Queue.getInstance().add(vCPU);
                        vCPU.startProcessingIOTaskTime = System.currentTimeMillis();
                        vCPU = VCPUScheduler.getInstance().pollVcpu();
                    }
                }
                PhysicalCPU pCPU = pCPUQueue.take();
                pCPU.loadVCPU(vCPU);
                executor.submit(pCPU);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
