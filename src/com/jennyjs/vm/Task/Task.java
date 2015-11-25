package com.jennyjs.vm.Task;

import java.util.Date;

/**
 * Created by jenny on 11/14/15.
 */
public class Task {
    final public int taskID;
    final int groupID;

    final TaskType taskType;
    final long createdTime;
    final long totalTime;
    long executedTime;

    public enum TaskType{
        IoTask,
        NonIoTask
    }

    public Task(long totalTime, TaskType taskType, int taskID, int groupID){
        executedTime = 0L;
        createdTime = System.currentTimeMillis();
        this.totalTime = totalTime;
        this.taskType = taskType;
        this.taskID = taskID;
        this.groupID = groupID;
    }

    public void increaseTime(long duration) {
        executedTime += duration;
    }

    public boolean isFinished() {
        return executedTime >= totalTime;
    }
    @Override
    public String toString(){
        return String.format(
               "<%s id=%s taskType=%s createdTime=%s>",
                this.getClass().getSimpleName(),
                this.taskID,
                this.taskType.name(),
                new Date(this.createdTime)
        );
    }
}
