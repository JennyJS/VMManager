package com.jennyjs.vm.ScheduleAlgorithm;

import com.jennyjs.vm.VCPU.VirtualCPU;

import java.util.Comparator;

/**
 * Created by jenny on 11/21/15.
 */
public class MRGComparator implements Comparator<VirtualCPU> {

    @Override
    public int compare(VirtualCPU o1, VirtualCPU o2) {
        if (o1.p.priorityLevel() > o2.p.priorityLevel()){
            return 1;
        } else if (o1.p.priorityLevel() == o2.p.priorityLevel()){
            return o1.clusterId - o2.clusterId;
        } else {
            return 0;
        }
    }
}
