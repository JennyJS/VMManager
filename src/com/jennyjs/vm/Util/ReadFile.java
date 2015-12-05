package com.jennyjs.vm.Util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manasa on 12/4/2015.
 */
public class ReadFile {

    public static List<TaskInfo> ReadInputFile() {
        int taskCount = 0, index;
        String[] splitOut;
        String fileName = "C:/Users/Manasa/Downloads/VM/VM/src/com/jennyjs/vm/input.txt";

        List<TaskInfo> list = null;
        try {
            //Create object of FileReader
            FileReader inputFile = new FileReader(fileName);

            //Instantiate the BufferedReader Class
            BufferedReader bufferReader = new BufferedReader(inputFile);
            String strLine;

            strLine = bufferReader.readLine();
            index = strLine.indexOf('#');
            while (index == 0) {
                strLine = bufferReader.readLine();
                index = strLine.indexOf('#');
            }

            if (index > 0) {
                splitOut = strLine.split("#");
                FileResult.numTasks = Integer.parseInt(splitOut[0].trim());
            } else {
                FileResult.numTasks = Integer.parseInt(strLine);
            }

            if (FileResult.numTasks > Constants.MAXTASKS) {
                System.out.println(" Invalid Input,Number of Tasks is greater than maximum number of tasks supported");
                System.exit(0);

            }
            System.out.println("Number of tasks:" + FileResult.numTasks);

            strLine = bufferReader.readLine();
            index = strLine.indexOf("#");
            while (index == 0) {
                strLine = bufferReader.readLine();
                index = strLine.indexOf("#");
            }

            if (index > 0) {
                splitOut = strLine.split("#");
                FileResult.numVms = Integer.parseInt(splitOut[0].trim());
            } else {
                FileResult.numVms = Integer.parseInt(strLine);
            }

            if (FileResult.numVms > Constants.MAXVMS) {
                System.out.println(" Invalid Input,Number of VMs is greater than maximum number of VMs supported");
                System.exit(0);

            }
            System.out.println("Number of VMs:" + FileResult.numVms);

            strLine = bufferReader.readLine();
            index = strLine.indexOf("#");
            while (index == 0) {
                strLine = bufferReader.readLine();
                index = strLine.indexOf("#");
            }

            if (index > 0) {
                splitOut = strLine.split("#");
                FileResult.numVcpu = Integer.parseInt(splitOut[0].trim());
            } else {
                FileResult.numVcpu = Integer.parseInt(strLine);
            }

            if (FileResult.numVcpu > Constants.MAXVCPU) {
                System.out.println(" Invalid Input,Number of VCPUs is greater than maximum number of VCPUs supported");
                System.exit(0);

            }
            System.out.println("Number of Vcpu:" + FileResult.numVcpu);

            strLine = bufferReader.readLine();
            index = strLine.indexOf("#");
            while (index == 0) {
                strLine = bufferReader.readLine();
                index = strLine.indexOf("#");
            }

            if (index > 0) {
                splitOut = strLine.split("#");
                FileResult.numGroups = Integer.parseInt(splitOut[0].trim());
            } else {
                FileResult.numGroups = Integer.parseInt(strLine);
            }

            System.out.println("Number of Groups:" + FileResult.numGroups);

            list = new ArrayList<>();

            while ((strLine = bufferReader.readLine()) != null) {
                if (strLine.indexOf("#") != 0) {
                    if (taskCount < FileResult.numTasks) {
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

            if (list.size() < FileResult.numTasks) {
                System.out.println(" Invalid Input,Task List is less than Number of Tasks");
                System.exit(0);
            }
            inputFile.close();
        } catch (Exception e) {
            System.out.println("Error while reading file line by line:" + e.getMessage());
            System.exit(0);
        }
        return list;
    }
}