import se.mau.DA343A.VT25.projekt.ServerGUI;
import se.mau.DA343A.VT25.projekt.net.ListeningSocket;
import se.mau.DA343A.VT25.projekt.net.ListeningSocketConnectionWorker;

import javax.swing.*;

public class Server extends ListeningSocket {

 private ServerGUI serverGUI;

 //gui måste visa consumption values så behövs referens till consumptiondata
 private ConsumptionData consumptionData;

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

    double consumption =0;
    public void getTotalConsumption(){

      //  serverGUI.getTotalConsumption();
         consumption = consumptionData.calculateConsumption();
    }

    public void setTotaltConsumptionGUi()
    {
        serverGUI.setTotalConsumption(consumption);
    }


}