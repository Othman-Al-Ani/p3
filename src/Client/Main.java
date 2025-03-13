package Client;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Client client = new Client(500, "Kyl", "localhost", 42069);
        Thread t = new Thread(client);
        t.start();
        Thread.sleep(10000);
        Client client1 = new Client(500, "Kyl", "localhost", 42069);
        Thread t1 = new Thread(client1);
        t1.start();
    }
}
