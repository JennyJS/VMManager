package com.jennyjs.vm;

import com.jennyjs.vm.PCPU.PCPUManager;
import com.jennyjs.vm.PCPU.PhysicalCPU;
import com.jennyjs.vm.Task.Task;
import com.jennyjs.vm.Task.TaskGenerator;
import com.jennyjs.vm.Util.Constants;
import com.jennyjs.vm.Util.TaskInfo;
import com.jennyjs.vm.VCPU.VCPUManager;
import com.jennyjs.vm.VCPU.VirtualCPU;
import com.jennyjs.vm.VCPU.VCPUConnectorQueue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jenny on 11/14/15.
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {

        //generating tasks
        int numTasks =  0,numVms = 0,numVcpu = 0,numGroups = 0,taskCount = 0,index;
        String[] splitOut;
        String fileName = "C:/Users/Manasa/Downloads/VM/manhongren-coen283p3-76266bd0bd37/src/com/jennyjs/vm/input.txt";
        try{
            //Create object of FileReader
            FileReader inputFile = new FileReader(fileName);

            //Instantiate the BufferedReader Class
            BufferedReader bufferReader = new BufferedReader(inputFile);
            String strLine;

            strLine = bufferReader.readLine();
            index = strLine.indexOf('#');
            while(index == 0)
            {
                strLine = bufferReader.readLine();
                index = strLine.indexOf('#');
            }

            if(index > 0) {
                splitOut = strLine.split("#");
                numTasks = Integer.parseInt(splitOut[0].trim());
            }
            else{
                numTasks = Integer.parseInt(strLine);
            }

            if(numTasks > Constants.MAXTASKS)
            {
                System.out.println(" Invalid Input,Number of Tasks is greater than maximum number of tasks supported");
                System.exit(0);

            }
            System.out.println("Number of tasks:" + numTasks);

            strLine = bufferReader.readLine();
            index = strLine.indexOf("#");
            while(index == 0)
            {
                strLine = bufferReader.readLine();
                index = strLine.indexOf("#");
            }

            if(index > 0){
                splitOut = strLine.split("#");
                numVms = Integer.parseInt(splitOut[0].trim());
            }
            else {
                numVms = Integer.parseInt(strLine);
            }

            if(numVms > Constants.MAXVMS)
            {
                System.out.println(" Invalid Input,Number of VMs is greater than maximum number of VMs supported");
                System.exit(0);

            }
            System.out.println("Number of VMs:" + numVms);

            strLine = bufferReader.readLine();
            index = strLine.indexOf("#");
            while(index == 0)
            {
                strLine = bufferReader.readLine();
                index = strLine.indexOf("#");
            }

            if(index > 0){
                splitOut = strLine.split("#");
                numVcpu = Integer.parseInt(splitOut[0].trim());
            }
            else {
                numVcpu = Integer.parseInt(strLine);
            }

            if(numVcpu > Constants.MAXVCPU)
            {
                System.out.println(" Invalid Input,Number of VCPUs is greater than maximum number of VCPUs supported");
                System.exit(0);

            }
            System.out.println("Number of Vcpu:" + numVcpu);

            strLine = bufferReader.readLine();
            index = strLine.indexOf("#");
            while(index == 0)
            {
                strLine = bufferReader.readLine();
                index = strLine.indexOf("#");
            }

            if(index > 0){
                splitOut = strLine.split("#");
                numGroups = Integer.parseInt(splitOut[0].trim());
            }
            else {
                numGroups = Integer.parseInt(strLine);
            }

            System.out.println("Number of Groups:" + numGroups);

            List<TaskInfo> list = new ArrayList<>();

            while ((strLine = bufferReader.readLine()) != null) {
                if(strLine.indexOf("#") != 0) {
                    if (taskCount < numTasks) {
                        splitOut = strLine.split(" ");
                        TaskInfo task = new TaskInfo();
                        task.groupNum = Integer.parseInt(splitOut[0]);
                        task.tasktype = splitOut[1];
                        task.executionTime = Integer.parseInt(splitOut[2]);
                        list.add(task);
                        taskCount++;
                    } else {
                        break;
                    }
                }
            }

            if(list.size() < numTasks)
            {
                System.out.println(" Invalid Input,Task List is less than Number of Tasks");
                System.exit(0);

            }

            inputFile.close();

        Thread taskGeneratorThread = new Thread(new TaskGenerator(list));
        taskGeneratorThread.run();

        //create VCPUs and add to VCPUQueue
        for(int i = 0; i < numVcpu; i++){
            VirtualCPU v = new VirtualCPU(1,1,i,1, VirtualCPU.Priority.under);//int clusterId, int vmId, int vCpuId, int credit, Priority p
            VCPUConnectorQueue.getInstance().add(v);
        }

        //load task to VCPU, put the VCPUs into the run queue and sort based on MRG
        VCPUManager.getInstance(VCPUManager.ScheduleType.MRG).start();


        // create several PCPUs and put them into the PCPU queue
        for (int j = 0; j < Constants.PCPUNUMBER; j++){
            PCPUManager.getInstance().addPCUP(new PhysicalCPU(j, PhysicalCPU.Status.idle));
        }
        

        // allocate the vCPUs in the runQueue to the idle pCPUs
        PCPUManager.getInstance().run();
        }
        catch(Exception e){
            System.out.println("Error while reading file line by line:" + e.getMessage());
        }
    }
}
