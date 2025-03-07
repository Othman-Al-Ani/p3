import se.mau.DA343A.VT25.projekt.net.ListeningSocketConnectionWorker;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Timer;
import java.util.TimerTask;

public class ConnectionWorker implements ListeningSocketConnectionWorker
{

    // denna klass tar in data info, men datan hanteras i consumptiondata

    private final ConsumptionData consumptionData = new ConsumptionData(); // instansiear denna för vi behöver få åtkomst til ldess metoder

    Timer timerData = new Timer();

    //inläsning av client info
    @Override
    public void newConnection(SocketAddress socketAddress, DataInput dataInput, DataOutput dataOutput)
    {
        //i run skriv metoden
        TimerTask fetchClientData = new TimerTask()
        {
            @Override
            public void run()
            {
                try
                {
                    //inlösing av data frpn client
                    String applianceName = dataInput.readUTF();
                    double consumptionValue = dataInput.readInt(); // consumption värde

                    //kontroll av värden:
                    // behövs kontrollen ?

                    //skickar in datan till conusmptiondata.
                    consumptionData.updateApplienceValue(applianceName,consumptionValue);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        timerData.scheduleAtFixedRate(fetchClientData,0,1000);



    }
}
