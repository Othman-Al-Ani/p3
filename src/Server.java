import se.mau.DA343A.VT25.projekt.ServerGUI;
import se.mau.DA343A.VT25.projekt.net.ListeningSocket;
import se.mau.DA343A.VT25.projekt.net.ListeningSocketConnectionWorker;

import javax.swing.*;

public class Server extends ListeningSocket {

 private ServerGUI serverGUI;

    public Server(int listeningPort) {
        super(listeningPort);
        serverGUI = new ServerGUI("Server GUI");

        //vad gör denna nedan
        SwingUtilities.invokeLater(()->serverGUI.createAndShowUI());
    }



    @Override
    public ListeningSocketConnectionWorker createNewConnectionWorker() {
        return new ConnectionWorker();
    }
}