import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by jenny on 11/15/15.
 */
public class VirtualCPUQueue {
    private final LinkedBlockingDeque<VirtualCPU> queue = new LinkedBlockingDeque<>();
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

    public VirtualCPU poll() throws InterruptedException{
        return this.queue.take();
    }
}
