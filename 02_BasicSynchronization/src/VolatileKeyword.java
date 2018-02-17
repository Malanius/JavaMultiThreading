import java.util.Scanner;

public class VolatileKeyword {

    public static void main(String[] args) {
        Processor proc1 = new Processor();
        proc1.start();

        System.out.println("Pres enter to stop.");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
        proc1.shutdow();
    }
}

class Processor extends Thread {

    private volatile boolean running = true;

    @Override
    public void run() {
        while (running) {
            System.out.println("Hello");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void shutdow() {
        running = false;
    }
}
