import java.io.IOException;
import java.util.Random;
import java.util.concurrent.*;

public class CalFutApp {

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();

        Future<Integer> future = service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("Starting");
                Random random = new Random();
                int time = random.nextInt(4_000);

                if (time > 2_000) {
                    throw new IOException("Sleeping for too long..."); //Will cause execution exception
                }

                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Finished");
                return time;
            }
        });

        service.shutdown();

        try {
            System.out.println("Result is: " + future.get()); //get is blocking
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
            System.out.println(e.getCause().getMessage());
        }
    }
}
