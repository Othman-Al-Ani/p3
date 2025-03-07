import se.mau.DA343A.VT25.projekt.net.ListeningSocketConnectionWorker;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;

public class ConnectionWorker implements ListeningSocketConnectionWorker
{


    //inläsning av client info
    @Override
    public void newConnection(SocketAddress socketAddress, DataInput dataInput, DataOutput dataOutput)
    {
        try {
            dataInput.readInt();
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
