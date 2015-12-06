package com.jennyjs.vm;

import com.jennyjs.vm.PCPU.PCPUManager;
import com.jennyjs.vm.PCPU.PhysicalCPU;
import com.jennyjs.vm.Task.TaskGenerator;
import com.jennyjs.vm.Util.Constants;
import com.jennyjs.vm.Util.ReadFile;
import com.jennyjs.vm.Util.TaskInfo;
import com.jennyjs.vm.VCPU.VCPUManager;
import com.jennyjs.vm.VCPU.VirtualCPU;
import com.jennyjs.vm.VCPU.VCPUConnectorQueue;
import java.util.Arrays;
import java.util.List;
import com.jennyjs.vm.Util.FileResult;

/**
 * Created by jenny on 11/14/15.
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
            //Reading Configuration Parameters
            ReadFile.ReadConfigFile();
            //Reading Input File
            List<TaskInfo> list = ReadFile.ReadInputFile();
            //Start the Task generator thread
            Thread taskGeneratorThread = new Thread(new TaskGenerator(list));
            taskGeneratorThread.run();

            FileResult.VmsTotalVcpuWt = new int[FileResult.numVms]; //Array stores total weight of vCpu in a VM
            Arrays.fill(FileResult.VmsTotalVcpuWt, 0);

            int vCpuId = 0,VmId = 0;

            //Configuring the number of vcpu per vm
            int vcpuPerVm = (int)Math.ceil((double)FileResult.numVcpu/FileResult.numVms);

            //create VCPUs and add to VCPUQueue.
            for(int i = 0; i < FileResult.numVcpu; i++){
                //System.out.println("Enter Loop " + i);
                int weight = FileResult.vcpuCredits.get(i);
                if(vCpuId >= vcpuPerVm) {
                    vCpuId = 0;
                    VmId++;
                }
                VirtualCPU v = new VirtualCPU(VmId, vCpuId, weight); //VM id,Vcpu Id,weight
                VCPUConnectorQueue.getInstance().add(v);
                FileResult.VmsTotalVcpuWt[VmId] += weight;
                //System.out.println("vmid "+VmId + "vcpu id "+vCpuId + "weight "+ weight);
                vCpuId++;
            }

            /*System.out.println("Array containing total weights of all Vcpu in the VM");
            for(int i = 0; i < FileResult.numVms; i++){
                System.out.println("VM " + i + ": " +FileResult.VmsTotalVcpuWt[i]);
            }*/

            //load task to VCPU, put the VCPUs into the run queue and sort based on MRG
            VCPUManager.getInstance(VCPUManager.ScheduleType.MRG).start();

        // create several PCPUs and put them into the PCPU queue
        for (int j = 0; j < Constants.PCPUNUMBER; j++){
            PCPUManager.getInstance().addPCUP(new PhysicalCPU(j, PhysicalCPU.Status.idle));
        }

        // allocate the vCPUs in the runQueue to the idle pCPUs
        PCPUManager.getInstance().run();
    }
}
