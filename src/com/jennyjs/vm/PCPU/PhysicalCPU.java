package com.jennyjs.vm.PCPU;

import com.jennyjs.vm.ScheduleAlgorithm.VCPUScheduler;
import com.jennyjs.vm.Util.Constants;
import com.jennyjs.vm.Util.ParseResult;
import com.jennyjs.vm.VCPU.VCPUManager;
import com.jennyjs.vm.VCPU.VirtualCPU;

/**
 * Created by jenny on 11/21/15.
 */
public class PhysicalCPU implements Runnable{

    final int pCPUId;
    public Status status;

    private VirtualCPU virtualCPU;
    public static int CompletedTaskCount = 0;

    @Override
    public void run() {
        try {
            Thread.sleep(Constants.MAX_PCPU_PROCESSING_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        virtualCPU.task.calculateExecutedTime(Constants.MAX_PCPU_PROCESSING_TIME);
        virtualCPU.task.TotalExecutionTime = (System.currentTimeMillis() - virtualCPU.task.createdTime);
        System.out.println("Task " + virtualCPU.task.taskID + " isFinished ? " + (virtualCPU.task.isFinished() ? "Yes " : "No ") + "Total execution time: " + virtualCPU.task.TotalExecutionTime + "ms ");

        if (virtualCPU.task.isFinished()){
            System.out.println(" ------- Task "+virtualCPU.task.taskID+" completed execution "+ " [ Total Execution Time = "+virtualCPU.task.TotalExecutionTime+" ] ------------");
            unloadTask();
            VCPUManager.VCPUConnectorQueue.getInstance().add(virtualCPU);
            CompletedTaskCount++;
        } else {
            VCPUScheduler.getInstance().addVcpu(virtualCPU);
        }

        if(CompletedTaskCount >= virtualCPU.parseResult.numTasks){
            System.setOut(ParseResult.stdout);
            System.out.println("----- Completed Processing All Tasks in the Input File [ Total Tasks Executed : "+virtualCPU.parseResult.numTasks + "ms ] ------");
            System.exit(0);
        }
        PCPUManager.getInstance().addPCUP(this);
    }

    public enum Status{
        busy(0),
        idle(1);
        private int level;
        Status(int l){
            this.level = l;
        }

        public int pCPULevel(){
            return level;
        }
    }

    public PhysicalCPU(int pCPUId){
        this.pCPUId = pCPUId;
        this.status = Status.idle;
    }

    public void loadVCPU(VirtualCPU vCPU){
        this.virtualCPU = vCPU;
        this.status = Status.busy;
        vCPU.credit -= Constants.MAX_PCPU_PROCESSING_TIME;
        if (!vCPU.withCreditLeft()){
            vCPU.p = VirtualCPU.Priority.under;
        }
    }

    private void unloadTask() {
        virtualCPU.isBusy = false;
        virtualCPU.task = null;
        this.status = Status.idle;
    }

    @Override
    public String toString(){
        return "PCPU Id is " + this.pCPUId;
    }
}
