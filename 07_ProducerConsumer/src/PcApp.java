import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

public class PcApp {

    private static BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            try {
                produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
    }

    private static void produce() throws InterruptedException {
        Random random = new Random();
        while (true) {
            queue.put(random.nextInt(100)); //Put will wait until there is space in the queue
        }
    }

    private static void consume() throws InterruptedException {
        Random random = new Random();

        while (true) {
            Thread.sleep(100);

            if (random.nextInt(10) == 0) {
                Integer value = queue.take(); //Take waits until something is in the queue

                System.out.println("Taken value: " + value);
                System.out.println("Queue size is:" + queue.size());
            }
        }
    }
}
