
import se.mau.DA343A.VT25.projekt.ServerGUI;
import se.mau.DA343A.VT25.projekt.net.ListeningSocketConnectionWorker;
import se.mau.DA343A.VT25.projekt.net.SecurityTokens;

import javax.swing.*;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.net.SocketAddress;


public class ConnectionWorker implements ListeningSocketConnectionWorker
{

    // denna klass tar in data info, men datan hanteras i consumptiondata
    private final Consumption consumption;
    private final ServerGUI serverGUI;
    private final SecurityTokens securityTokens;
    private String applianceName;

    public ConnectionWorker(Consumption consumption, ServerGUI  serverGUI, SecurityTokens securityTokens){
        this.consumption = consumption;
        this.serverGUI = serverGUI;
        this.securityTokens = securityTokens;
    }

    //inläsning av client info
    @Override
    public void newConnection(SocketAddress socketAddress, DataInput dataInput, DataOutput dataOutput) {
        String tName = Thread.currentThread().getName();

        try {
            String token = dataInput.readUTF();
            if(!securityTokens.verifyToken(token)){
                return;
            }
            applianceName = dataInput.readUTF();
            double consumptionValue = dataInput.readInt();
            SwingUtilities.invokeLater(() -> serverGUI.addLogMessage("New connection worker started on " + tName + " -> " + applianceName));
            ConsumptionData data = new ConsumptionData(applianceName, consumptionValue);
            consumption.addAppliance(data);

            while(true)
            {
                //kontroll om client är connected eller frånkopplad:

                int messageType = dataInput.readInt();
                System.out.println(messageType + "-Type");///errro här

                switch (messageType)
                {
                    case 1://öppen

                        consumptionValue = dataInput.readInt();
                        data.setValue(consumptionValue);
                        //skickar in de för sp datan hanteras
                        break;

                    case 0:
                        data.setValue(0);
                        SwingUtilities.invokeLater(() -> serverGUI.addLogMessage("Worker " + tName + " : diconnected" + " -> " + applianceName  ));
                        return;
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
