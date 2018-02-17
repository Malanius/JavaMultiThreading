import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Worker {

    Random random = new Random();

    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    private List<Integer> list1 = new ArrayList<>();
    private List<Integer> list2 = new ArrayList<>();

    void stage1() {
        synchronized (lock1) {
            try {
                //Pretend getting data from somewhere
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            list1.add(random.nextInt());
        }
    }

    void stage2() {
        synchronized (lock2) {
            try {
                //Pretend getting data from somewhere
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            list2.add(random.nextInt());
        }
    }

    public void process() {
        for (int i = 0; i < 1_000; i++) {
            stage1();
            stage2();
        }
    }

    public void main() {
        System.out.println("Starting...");

        long start = System.currentTimeMillis();

        //process(); around ~4s proc time on my system
        //using synchronized stageX()- ~6s, caused by just one lock for Worker.class
        //using synchronized blocks ~3s
        Thread t1 = new Thread(this::process);
        Thread t2 = new Thread(this::process);

        t1.start();
        t2.start();


        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        long end = System.currentTimeMillis();

        System.out.println("Time taken: " + (end - start));
        System.out.println("List 1: " + list1.size());
        System.out.println("List 2: " + list2.size());
    }
}
