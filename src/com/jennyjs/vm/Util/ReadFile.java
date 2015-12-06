package com.jennyjs.vm.Util;

import com.jennyjs.vm.Task.Task;
import com.jennyjs.vm.VCPU.VirtualCPU;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manasa on 12/4/2015.
 */
public class ReadFile {

    public static ParseResult parseFile(final String pathToFile) throws IOException, ParseException {
        int numTasks;
        int numVms;
        int numVcpu;
        int numGroups;
        final List<Integer> grpCredits = new ArrayList<>();
        final List<Integer> vcpuCredits = new ArrayList<>();
        final List<Integer> vmsTotalVcpuWt = new ArrayList<>();
        final List<Task> taskInfo = new ArrayList<>();
        final List<VirtualCPU> vcpus = new ArrayList<>();

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(pathToFile));
        JSONObject jsonObject = (JSONObject) obj;
        numTasks = ((Number)jsonObject.get("numTask")).intValue();
        numVms = ((Number)jsonObject.get("NumVM")).intValue();
        JSONArray vCpulst = (JSONArray)jsonObject.get("VcpuCredits");
        numVcpu = vCpulst.size();
        JSONArray groupLst = (JSONArray)jsonObject.get("GroupCredits");
        numGroups = groupLst.size();
        JSONArray taskArr = (JSONArray)jsonObject.get("tasks");


        for (int i = 0; i < numVms; i++) {
            vmsTotalVcpuWt.add(0);
        }

        int taskId = 0;
        for (Object aTaskArr : taskArr) {
            JSONObject task = (JSONObject) aTaskArr;
            //long totalTime, TaskType taskType, int taskID, int groupID
            taskInfo.add(new Task(
                    ((Number) task.get("exeTime")).intValue(),
                    Task.TaskType.mapFromString((String) task.get("type")),
                    taskId++,
                    ((Number) task.get("groupId")).intValue()));
        }

        for (Object aGroupLst : groupLst) {
            grpCredits.add(((Number) aGroupLst).intValue());
        }

        for (Object aVCpulst : vCpulst) {
            vcpuCredits.add(((Number) aVCpulst).intValue());
        }


        int vCpuId = 0,vmId = 0;

        //Configuring the number of vcpu per vm
        int vcpuPerVm = (int)Math.ceil((double) numVcpu / numVms);

        //create VCPUs and add to VCPUQueue.
        for(int i = 0; i < numVcpu; i++){
            int weight = vcpuCredits.get(i);
            if(vCpuId >= vcpuPerVm) {
                vCpuId = 0;
                vmId++;
            }
            vcpus.add(new VirtualCPU(vmId, vCpuId, weight));
            vmsTotalVcpuWt.set(vmId, vmsTotalVcpuWt.get(vmId) + weight);
            vCpuId++;
        }

        return new ParseResult(numTasks, numVms, numVcpu, numGroups, grpCredits, vcpuCredits, vmsTotalVcpuWt, taskInfo, vcpus);
    }
}
