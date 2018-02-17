import java.util.Scanner;

public class WaitApp {

    public static void main(String[] args) throws InterruptedException {
        final Processor processor = new Processor();

        Thread t1 = new Thread(() -> {
            try {
                processor.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                processor.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

    }
}

class Processor {
    public void produce() throws InterruptedException {
        synchronized (this) {
            System.out.println("Producer thread running");
            wait(); //Can be used only in synchronized block, releases the lock
            System.out.println("Producer resumed");
        }
    }

    public void consume() throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        Thread.sleep(2_000);

        synchronized (this) {
            System.out.println("Waiting for return key...");
            sc.nextLine();
            System.out.println("Return key pressed");
            notify(); //Can only by called in synchronized block, notifies other threads locked on same objects, lock is released after synchronized block end
            Thread.sleep(5_000); //Will postpone lock release for notified threads
        }
    }
}
