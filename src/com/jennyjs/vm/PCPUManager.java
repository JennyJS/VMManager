package com.jennyjs.vm;


import com.jennyjs.vm.ScheduleAlgorithm.MRGComparator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by jenny on 11/22/15.
 */
public class PCPUManager implements Runnable {
    private static PCPUManager pcpuManager;
    public static PCPUManager getInstance(){
        if (pcpuManager == null){
            pcpuManager = new PCPUManager();
        }
        return pcpuManager;
    }



    @Override
    public void run() {
//        while(true){
            ExecutorService executor = Executors.newFixedThreadPool(5);
            Runnable job = new PCPUManager();
            executor.execute(job);
            executor.shutdown();

            while (!executor.isTerminated()){}
            System.out.println("Finish all the jobs!");
            System.out.println(Thread.currentThread().getName() + " start. ");
            MRGComparator mrgComparator = new MRGComparator();

            Scheduler.getInstance(mrgComparator).pollVcpu();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " end. ");
//        }

    }
}
