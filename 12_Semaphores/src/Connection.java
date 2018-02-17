import java.util.concurrent.Semaphore;

public class Connection {

    private static Connection instance = new Connection();
    private int connections = 0;

    private Semaphore semaphore = new Semaphore(10, true);


    private Connection() {

    }

    public static Connection getInstance() {
        return instance;
    }

    public void connect() {
        try {
            semaphore.acquire();
            doConnect();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }

    public void doConnect() {
        synchronized (this) {
            connections++;
            System.out.println("Connected. Current connections: " + connections);
        }

        try {
            Thread.sleep(2_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (this) {
            connections--;
            System.out.println("Disconnected. Current connections: " + connections);
        }
    }


}
