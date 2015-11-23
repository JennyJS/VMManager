package com.jennyjs.vm.VCPU;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by jenny on 11/15/15.
 */
public class VirtualCPUQueue {
    private final BlockingQueue<VirtualCPU> queue = new LinkedBlockingQueue<>();
    private static VirtualCPUQueue VirtualCPUQueue;

    public static VirtualCPUQueue getInstance(){
        if (VirtualCPUQueue == null){
            VirtualCPUQueue = new VirtualCPUQueue();
        }
        return VirtualCPUQueue;
    }

    public void add(VirtualCPU virtualCPU){
        this.queue.add(virtualCPU);
    }

    public VirtualCPU poll() throws InterruptedException {
        return this.queue.take();
    }
}
