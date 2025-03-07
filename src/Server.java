import se.mau.DA343A.VT25.projekt.net.ListeningSocket;
import se.mau.DA343A.VT25.projekt.net.ListeningSocketConnectionWorker;

public class Server implements ListeningSocket {
    public Server(int port) {
    }


    @Override
    public ListeningSocketConnectionWorker createNewConnectionWorker() {
        return null;
    }
}