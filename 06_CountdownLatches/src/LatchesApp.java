import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LatchesApp {

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(3);

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 3; i++) {
            executorService.submit(new Process(latch));
        }
        executorService.shutdown();

        try {
            latch.await(); //Will wait until latch countdown reaches zero
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Completed!");
    }
}

class Process implements Runnable {

    private CountDownLatch countDownLatch;

    Process(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        System.out.println("Started.");
        try {
            Thread.sleep(3_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        countDownLatch.countDown();
    }
}
