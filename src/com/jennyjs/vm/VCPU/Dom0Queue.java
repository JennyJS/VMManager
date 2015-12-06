package com.jennyjs.vm.VCPU;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by jenny on 12/5/15.
 */
public class Dom0Queue {

    private final BlockingQueue<VirtualCPU> queue = new LinkedBlockingQueue<>();
    private static Dom0Queue dom0Queue;
    public static Dom0Queue getInstance (){
        if (dom0Queue == null){
            dom0Queue = new Dom0Queue();
        }
        return dom0Queue;
    }
    public void add(VirtualCPU virtualCPU){
        this.queue.add(virtualCPU);
    }

    public VirtualCPU poll(){
        return this.queue.poll();
    }

    public VirtualCPU peek(){
        return this.queue.peek();
    }
}
