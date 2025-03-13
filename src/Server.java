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
import java.util.Vector;

public class Server extends ListeningSocket {

    private ServerGUI serverGUI;
    private Consumption consumption;

    private Timer timer;
    private LiveXYSeries<Double> series;

    private SecurityTokens securityTokens;

    private Vector<LiveXYSeries> applianceSeries;


    public Server(int listeningPort) {
        super(listeningPort);
        serverGUI = new ServerGUI("Server GUI");
        this.consumption = new Consumption(this);
        timer = new Timer();
        series = new LiveXYSeries<>("Comsumption data", 20);
        applianceSeries = new Vector<>();


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

                        for (int i = 0; i < applianceSeries.size()-1; i++) {

                            int finalI = i;
                            SwingUtilities.invokeLater(() -> applianceSeries.get(finalI).addValue((double) (System.currentTimeMillis()/1000), consumption.applianceValue(finalI)));

                        }
                    }
                }, 0, 1000);
    }

    public void setApplianceSeries(String name ){
        applianceSeries.add(new LiveXYSeries<>(name, 20));
        SwingUtilities.invokeLater(()->serverGUI.addSeries(applianceSeries.lastElement()));
    }

    @Override
    public ListeningSocketConnectionWorker createNewConnectionWorker() {
        ConnectionWorker connectionWorker = new ConnectionWorker(consumption, serverGUI, securityTokens);

        return connectionWorker;
    }
}