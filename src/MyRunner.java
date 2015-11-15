/**
 * Created by jenny on 11/14/15.
 */
public class MyRunner {

    public static void main(String[] args) {
        Thread taskGeneratorThread = new Thread(new TaskGenerator(10));
        taskGeneratorThread.run();
    }
}
