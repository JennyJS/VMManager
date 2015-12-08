package com.jennyjs.vm.Util;

import com.jennyjs.vm.Task.Task;
import com.jennyjs.vm.VCPU.VCPUManager;
import com.jennyjs.vm.VCPU.VirtualCPU;

import java.io.PrintStream;
import java.util.Collections;
import java.util.List;

/**
 * An immutable class to represent result of parsing a file
 */
public class ParseResult {
    public final int numTasks;
    public final int numVms;
    public final int numVcpu;
    public final int numGroups;
    public final List<Integer> grpCredits;
    public final List<Integer> vcpuCredits;
    public final List<Integer> vmsTotalVcpuWt;
    public final List<Task> tasks;
    public final List<VirtualCPU> virtualCPUs;
    public final VCPUManager.ScheduleType type;
    public static PrintStream stdout;

    public ParseResult(final int numTasks,
                       final int numVms,
                       final int numVcpu,
                       final int numGroups,
                       final List<Integer> grpCredits,
                       final List<Integer> vcpuCredits,
                       final List<Integer> vmsTotalVcpuWt,
                       final List<Task> tasks,
                       final List<VirtualCPU> virtualCPUs,
                       final VCPUManager.ScheduleType type) {

        this.numTasks = numTasks;
        this.numVms = numVms;
        this.numVcpu = numVcpu;
        this.numGroups = numGroups;
        this.grpCredits = Collections.unmodifiableList(grpCredits);
        this.vcpuCredits = Collections.unmodifiableList(vcpuCredits);
        this.vmsTotalVcpuWt = Collections.unmodifiableList(vmsTotalVcpuWt);
        this.tasks = Collections.unmodifiableList(tasks);
        this.virtualCPUs = Collections.unmodifiableList(virtualCPUs);
        this.type = type;
    }
}
