import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Client client = new Client(15, "Laptop", "localhost",42069);
        Thread thread = new Thread(client);
        Client client1 = new Client(1000, "Hej", "localhost",42069);
        Thread thread1 = new Thread(client1);
        Client client2 = new Client(205, "Då", "localhost",42069);
        Thread thread2 = new Thread(client2);



        Server server = new Server(42069);

        Thread Sthread = new Thread(server);
        Sthread.start();
        thread.start();
        thread1.start();
        thread2.start();
    }
}