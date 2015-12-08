package com.jennyjs.vm.Task;

import java.util.Date;

/**
 * Created by jenny on 11/14/15.
 */

public class Task {
    final public int taskID;
    final public int groupID;

    public final TaskType taskType;
    public final long createdTime;
    public long TotalExecutionTime;
    final long totalTime;
    long executedTime;

    public enum TaskType{
        IoTask(1),
        NonIoTask(0);
        private final int priority;
        TaskType(int i){
            this.priority = i;
        }

        public int getPriority(){
            return priority;
        }



        public static TaskType mapFromString(String str){
            for (TaskType taskType : values()) {
                if (taskType.name().equals(str)) {
                    return taskType;
                }
            }

            return null;
        }
    }

    public Task(long totalTime, TaskType taskType, int taskID, int groupID){
        executedTime = 0L;
        createdTime = System.currentTimeMillis();
        this.totalTime = totalTime;
        this.taskType = taskType;
        this.taskID = taskID;
        this.groupID = groupID;
    }

    public void calculateExecutedTime(long duration) {
        executedTime += duration;
    }

    public boolean isFinished() {
        return executedTime >= totalTime;
    }
    @Override
    public String toString(){
        return String.format(
               "<%s id=%s taskType=%s totalTime=%sms executedTime=%sms createdTime=%s>",
                this.getClass().getSimpleName(),
                this.taskID,
                this.taskType.name(),
                this.totalTime,
                this.executedTime,
                new Date(this.createdTime)
        );
    }
}
