public class Main {
    public static void main(String[] args){
        Client client = new Client(30, "Laptop", "localhost",42069);
        Thread thread = new Thread(client);
        thread.start();


        Server server = new Server(42069);
        Thread Sthread = new Thread(server);
        Sthread.start();
    }
}