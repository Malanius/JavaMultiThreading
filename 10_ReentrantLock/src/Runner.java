import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Runner {

    private int count;

    private Lock lock = new ReentrantLock();
    private Condition cond = lock.newCondition();

    private void increment() {
        for (int i = 0; i < 10_000; i++) {
            count++;
        }
    }

    public void thread1() throws InterruptedException {
        lock.lock();

        System.out.println("Awaiting...");
        cond.await(); //similar to wait()
        System.out.println("Await over...");

        try {
            increment();
        } finally {
            lock.unlock();
        }
    }

    public void thread2() throws InterruptedException {
        Thread.sleep(1_000);
        lock.lock();

        System.out.println("Awaiting return key...");
        new Scanner(System.in).nextLine();
        System.out.println("Key pressed.");

        cond.signal();

        try {
            increment();
        } finally {
            lock.unlock();
        }
    }

    public void finished() {
        System.out.println("Count is: " + count);
    }
}
