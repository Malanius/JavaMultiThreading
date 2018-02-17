public class SynchronizedApp {

    //Could be overcome by AtomicInteger also
    private int count = 0;

    public static void main(String[] args) {
        SynchronizedApp app = new SynchronizedApp();
        app.doWork();
    }

    public void doWork() {

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10_000; i++) {
                increment();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10_000; i++) {
                increment();
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        System.out.println("Count is: " + count);
    }

    synchronized void increment() {
        count++;
    }
}
