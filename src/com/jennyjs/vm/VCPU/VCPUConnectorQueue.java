package com.jennyjs.vm.VCPU;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by jenny on 11/15/15.
 */
public class VCPUConnectorQueue {
    private final BlockingQueue<VirtualCPU> queue = new LinkedBlockingQueue<>();
    private static VCPUConnectorQueue VCPUConnectorQueue;

    public static VCPUConnectorQueue getInstance(){
        if (VCPUConnectorQueue == null){
            VCPUConnectorQueue = new VCPUConnectorQueue();
        }
        return VCPUConnectorQueue;
    }

    public void add(VirtualCPU virtualCPU){
        this.queue.add(virtualCPU);
    }

    public VirtualCPU poll() throws InterruptedException {
        return this.queue.take();
    }
}
