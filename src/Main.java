import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Client client = new Client(15, "Laptop", "localhost",42069);
        Thread thread = new Thread(client);



        Server server = new Server(42069);

        Thread Sthread = new Thread(server);
        Sthread.start();
        thread.start();
    }
}