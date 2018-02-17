import java.util.LinkedList;
import java.util.Random;

class Processor {

    LinkedList<Integer> list = new LinkedList<>();
    private final int limit = 10;
    private Object lock = new Object();

    public void produce() throws InterruptedException {

        int value = 0;

        while (true) {
            synchronized (lock) {
                while (list.size() == limit) {
                    lock.wait();
                }
                list.add(value++);
                lock.notify();
            }
        }
    }

    public void consume() throws InterruptedException {

        Random random = new Random();

        while (true) {
            synchronized (lock) {
                while (list.size() == 0) {
                    lock.wait();
                }
                System.out.print("List size is: " + list.size());
                int value = list.removeFirst();
                System.out.println("; Removed value: " + value);
                lock.notifyAll();
            }

            Thread.sleep(random.nextInt(1_000));
        }
    }
}
