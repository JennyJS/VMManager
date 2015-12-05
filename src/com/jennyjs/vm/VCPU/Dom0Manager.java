package com.jennyjs.vm.VCPU;

import com.jennyjs.vm.ScheduleAlgorithm.CreditComparator;
import com.jennyjs.vm.ScheduleAlgorithm.MRGComparator;
import com.jennyjs.vm.ScheduleAlgorithm.VCPUScheduler;
import com.jennyjs.vm.Task.Task;
import com.jennyjs.vm.Task.TaskQueue;

import java.util.Comparator;

/**
 * Created by YolandaSuee on 12/4/15.
 */
public class Dom0Manager extends Thread {


    private static Dom0Manager Dom0Manager;
    public static Dom0Manager getInstance(){
        if (Dom0Manager == null){
            Dom0Manager = new Dom0Manager();
        }
        return Dom0Manager;
    }

    private Dom0Manager(){}

    public void run() {

        try{
            sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try{
            VirtualCPU virtualCPU = VCPUConnectorQueue.getInstance().poll();
        }
        catch (InterruptedException e){
            System.out.print(e.getMessage());
        }


    }


}
