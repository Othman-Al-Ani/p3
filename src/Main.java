public class Main {

    public static void main(String[] args)
    {
        Server server = new Server(42069);
        Thread thread = new Thread(server);
        thread.start();
    }
}