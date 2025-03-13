import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        //Client client = new Client(750, "Laptop", "localhost",42069);
        //Thread thread = new Thread(client);
        //Client client1 = new Client(1000, "Hej", "localhost",42069);
        //Thread thread1 = new Thread(client1);
//        Client client2 = new Client(205, "Då", "localhost",42069);
//        Thread thread2 = new Thread(client2);

        Server server = new Server(42069);
        Thread sThread = new Thread(server);
        sThread.start();

        Client client1 = new Client(500, "Laptop", "localhost", 42069);
        Client client2 = new Client(1000, "Refrigerator", "localhost", 42069);
        Client client3 = new Client(1000, "Refrigerator", "localhost", 42069);
        Thread cThread1 = new Thread(client1);
        Thread cThread2 = new Thread(client2);
        Thread cThread3 = new Thread(client3);
        cThread1.start();
        cThread2.start();
        cThread3.start();

    }
}