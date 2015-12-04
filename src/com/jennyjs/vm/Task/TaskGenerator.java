package com.jennyjs.vm.Task;

import com.jennyjs.vm.Util.TaskInfo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jenny on 11/14/15.
 */


public class TaskGenerator implements Runnable {
    //private final int taskNum;
    static int taskID = 0;
    List<TaskInfo> list = new ArrayList<>();

    public TaskGenerator(List<TaskInfo> list) {
        this.list = list;
    }
    @Override
    public void run() {
       // int taskID = 0;
        //while (taskID <= taskNum)
        for(TaskInfo elem : list)
        {
            try {
                    Thread.sleep(1000);
                    Task t;
                    //System.out.println(" Task group :" + elem.groupNum + " Task Type :" + elem.tasktype + " Execution Time :" + elem.executionTime);
                    if (elem.tasktype.equals("IO"))
                        t = new Task(elem.executionTime, Task.TaskType.IoTask, ++taskID, elem.groupNum);
                    else
                        t = new Task(elem.executionTime, Task.TaskType.NonIoTask, ++taskID, elem.groupNum); //(long totalTime, TaskType taskType, int taskID, int groupID) Modify here!
                    TaskQueue.getInstance().addTask(t);
                    System.out.println("Generating..." + t);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
           /*Task t = new Task(10, Task.TaskType.NonIOTask, taskID, 1); //(long totalTime, TaskType taskType, int taskID, int groupID) Modify here!
            TaskQueue.getInstance().addTask(t);
            System.out.println("Generating..." + t);*/
        }
    }
}
