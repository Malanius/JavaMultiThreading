import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaforApp {

    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore = new Semaphore(1);
//        System.out.println("Available permits: " + semaphore.availablePermits());
//        semaphore.release();
//        System.out.println("Available after release: " + semaphore.availablePermits());
//        semaphore.acquire(); //will waits until some permit is available
//        System.out.println("Available after acquire: " + semaphore.availablePermits());

        ExecutorService service = Executors.newCachedThreadPool();

        for (int i = 0; i < 200; i++) {
            service.submit(() -> Connection.getInstance().connect());
        }
        service.shutdown();
        service.awaitTermination(1, TimeUnit.DAYS);
    }
}
