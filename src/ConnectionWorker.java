import se.mau.DA343A.VT25.projekt.IAppExitingCallback;
import se.mau.DA343A.VT25.projekt.ServerGUI;
import se.mau.DA343A.VT25.projekt.net.ListeningSocketConnectionWorker;
import se.mau.DA343A.VT25.projekt.net.SecurityTokens;

import javax.swing.*;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.net.SocketAddress;
import java.util.Timer;
import java.util.TimerTask;

public class ConnectionWorker implements ListeningSocketConnectionWorker
{

    // denna klass tar in data info, men datan hanteras i consumptiondata
    private Consumption consumption;
    private ServerGUI serverGUI;
    private SecurityTokens securityTokens;

    public ConnectionWorker(Consumption consumption, ServerGUI serverGUI, SecurityTokens securityTokens){
        this.consumption = consumption;
        this.serverGUI = serverGUI;
        this.securityTokens = securityTokens;
    }


    //inläsning av client info
    @Override
    public void newConnection(SocketAddress socketAddress, DataInput dataInput, DataOutput dataOutput) {
        SwingUtilities.invokeLater(() -> serverGUI.addLogMessage("New connection worker started on " + Thread.currentThread().getName()) );
        try {
            String token = dataInput.readUTF();
            if(!securityTokens.verifyToken(token)){
                return;
            }

            String applianceName = dataInput.readUTF();
            double consumptionValue = dataInput.readInt(); // consumption värde
            consumption.addAppliance(applianceName, consumptionValue);
            while (true) {
                consumptionValue = dataInput.readDouble();
                consumption.updateApplienceValue(applianceName, consumptionValue);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
