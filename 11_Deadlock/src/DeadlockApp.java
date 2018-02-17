public class DeadlockApp {

    public static void main(String[] args) throws InterruptedException {
        Runner runner = new Runner();

        Thread t1 = new Thread(() -> {
            try {
                runner.thread1();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                runner.thread2();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        runner.finished();
    }
}
