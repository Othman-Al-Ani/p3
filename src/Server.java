import se.mau.DA343A.VT25.projekt.LiveXYSeries;
import se.mau.DA343A.VT25.projekt.ServerGUI;
import se.mau.DA343A.VT25.projekt.net.ListeningSocket;
import se.mau.DA343A.VT25.projekt.net.ListeningSocketConnectionWorker;
import se.mau.DA343A.VT25.projekt.net.SecurityTokens;

import javax.swing.*;
import java.io.DataInput;
import java.io.DataOutput;
import java.util.Timer;
import java.util.TimerTask;

public class Server extends ListeningSocket {

    private ServerGUI serverGUI;
    private Consumption consumption;
    private Timer timer;
    private LiveXYSeries<Double> series;
    private SecurityTokens securityTokens;

    public Server(int listeningPort) {
        super(listeningPort);
        serverGUI = new ServerGUI("Server GUI");
        this.consumption = new Consumption();
        timer = new Timer();
        series = new LiveXYSeries<>("Total consumption", 20);

        SwingUtilities.invokeLater(()->serverGUI.createAndShowUI());
        SwingUtilities.invokeLater(()->serverGUI.addSeries(series));
        securityTokens = new SecurityTokens("goon");
        displayData();
    }

    //uppdaterar gui
    public void displayData(){
        timer.scheduleAtFixedRate(
                new TimerTask() {
                    @Override
                    public void run() {
                        SwingUtilities.invokeLater(()-> serverGUI.setTotalConsumption(consumption.calculateConsumption()));
                        SwingUtilities.invokeLater(() -> series.addValue((double) (System.currentTimeMillis()/1000), consumption.calculateConsumption()));
                    }
                }, 0, 1000);
    }

    @Override
    public ListeningSocketConnectionWorker createNewConnectionWorker() {
        ConnectionWorker connectionWorker = new ConnectionWorker(consumption, serverGUI, securityTokens);
        return connectionWorker;
    }



}